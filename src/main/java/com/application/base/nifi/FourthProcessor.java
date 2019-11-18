package com.application.base.nifi;

import com.alibaba.fastjson.JSON;
import com.application.base.model.Field;
import com.application.base.model.Record;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.nifi.annotation.behavior.InputRequirement;
import org.apache.nifi.annotation.behavior.SideEffectFree;
import org.apache.nifi.annotation.behavior.WritesAttribute;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.flowfile.attributes.CoreAttributes;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.ProcessorInitializationContext;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.io.StreamCallback;
import org.apache.nifi.processor.util.StandardValidators;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : 孤狼
 * @NAME: ThirdProcessor
 * @DESC: mysql 数据到 hive.
 **/
@SideEffectFree
@Tags({"MySql To Hive", "ThirdProcessor"})
@InputRequirement(InputRequirement.Requirement.INPUT_REQUIRED)
@CapabilityDescription("fetch data from mysql to hive.")
@WritesAttribute(attribute = "mime.type", description = "Sets the mime type to application/json")
public class FourthProcessor extends AbstractProcessor {
	
	/**
	 * hive 的表名.
	 */
	private static final PropertyDescriptor TABLE_NAME = new PropertyDescriptor.Builder()
			.name("Table Name")
			.description("use of generator insert sql of hive")
			.addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
			.required(true)
			.build();
	
	/**
	 * 成功标识.
	 */
	private static final Relationship REL_SUCCESS = new Relationship.Builder()
			.name("success")
			.description("A FlowFile is routed to this relationship after it has been converted to JSON")
			.build();
	
	/**
	 * 失败标识.
	 */
	private static final Relationship REL_FAILURE = new Relationship.Builder()
			.name("failure")
			.description("A FlowFile is routed to this relationship if it cannot be parsed as Avro or cannot be converted to JSON for any reason")
			.build();
	
	/**
	 * 属性介绍
	 */
	private List<PropertyDescriptor> properties;
	
	@Override
	protected void init(ProcessorInitializationContext context) {
		super.init(context);
		final List<PropertyDescriptor> properties = new ArrayList<>();
		properties.add(TABLE_NAME);
		this.properties = Collections.unmodifiableList(properties);
	}
	
	@Override
	protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
		return properties;
	}
	
	@Override
	public Set<Relationship> getRelationships() {
		final Set<Relationship> rels = new HashSet<>();
		rels.add(REL_SUCCESS);
		rels.add(REL_FAILURE);
		return rels;
	}
	
	@Override
	public void onTrigger(ProcessContext context, ProcessSession session) throws ProcessException {
		FlowFile flowFile = session.get();
		if (flowFile == null) {
			return;
		}
		
		//获取表hive表名.
		final String tableName = context.getProperty(TABLE_NAME).getValue();
		try {
			flowFile = session.write(flowFile, new StreamCallback() {
				@Override
				public void process(InputStream inputStream, OutputStream outputStream) throws IOException {
					final GenericData genericData = GenericData.get();
					final DataFileStream<GenericRecord> reader = new DataFileStream<>(inputStream, new GenericDatumReader<>());
					try {
						GenericRecord currRecord = null;
						if (reader.hasNext()) {
							currRecord = reader.next();
						}
						String record = genericData.toString(currRecord);
						getLogger().info("记录1信息是:"+record);
						String genSql = generatorSql(currRecord, tableName);
						getLogger().info("得到1的sql是:"+genSql);
						outputStream.write(genSql.getBytes());
						int count=2;
						while (reader.hasNext()) {
							count++;
							outputStream.write('\n');
							currRecord = reader.next(currRecord);
							getLogger().info("记录"+count+"的信息是:"+record);
							genSql = generatorSql(currRecord, tableName);
							getLogger().info("得到"+count+"的sql是:"+genSql);
							outputStream.write(genSql.getBytes());
						}
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			});
		} catch (final ProcessException pe) {
			getLogger().error("Failed to convert {} from Avro to JSON due to {}; transferring to failure", new Object[]{flowFile, pe});
			session.transfer(flowFile, REL_FAILURE);
			return;
		}
		//以json串的方式将sql写入.
		flowFile = session.putAttribute(flowFile, CoreAttributes.MIME_TYPE.key(), "application/json");
		session.transfer(flowFile, REL_SUCCESS);
	}
	
	/**
	 * 字符串操作.
	 * @param type
	 * @param value
	 * @param buffer
	 * @param delimiter
	 */
	private void toString(String type, Object value, StringBuffer buffer, String delimiter) {
		switch (type) {
			case "int":
			case "long":
			case "float":
			case "double":
			case "boolean":
				buffer.append(value).append(delimiter);
				break;
			case "string":
				buffer.append("\"").append(value).append("\"").append(delimiter);
				break;
			default:
				buffer.append(value).append(delimiter);
				break;
		}
	}
	
	/**
	 * 生成sql
	 * @param currRecord
	 * @param tableName
	 * @return
	 */
	public String generatorSql(GenericRecord currRecord, String tableName) {
		StringBuffer buffer = new StringBuffer();
		StringBuffer valueBuffer = new StringBuffer();
		buffer.append("insert into " + tableName);
		Schema schema = currRecord.getSchema();
		Record recd = JSON.parseObject(schema.toString(), Record.class);
		List<Field> recdFields = recd.getFields();
		getLogger().info(schema.toString());
		List<Schema.Field> fields = schema.getFields();
		if (fields.size() > 0) {
			valueBuffer.append("values(");
			int count = 0;
			for (Schema.Field field : fields) {
				String fieldName = field.name();
				Object obj = currRecord.get(fieldName);
				if (fields.size() - 1 > count) {
					
					for (Field f : recdFields) {
						if (f.getName().equals(fieldName)) {
							String type = f.getType()[1];
							this.toString(type, obj, valueBuffer, ",");
							break;
						}
					}
					
				} else {
					for (Field f : recdFields) {
						if (f.getName().equals(fieldName)) {
							String type = f.getType()[1];
							this.toString(type, obj, valueBuffer, "");
							break;
						}
					}
				}
				count++;
			}
			valueBuffer.append(")");
		}
		buffer.append(" ").append(valueBuffer).append(";");
		getLogger().info(buffer.toString());
		return buffer.toString();
	}
}
