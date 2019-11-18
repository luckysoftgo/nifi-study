package com.application.base.processor;

import com.application.base.cont.NiFiConstant;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.nifi.annotation.behavior.SideEffectFree;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.ProcessorInitializationContext;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.io.InputStreamCallback;
import org.apache.nifi.processor.io.OutputStreamCallback;
import org.apache.nifi.processor.util.StandardValidators;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : 孤狼
 * @NAME: FirstProcessor
 * @DESC: 判断文本中是否有某个值.
 **/

@Tags({"first-example:fetch value from json string"})
@SideEffectFree
@CapabilityDescription("fetch value from json string.")
public class FirstProcessor extends AbstractProcessor {
	
	private List<PropertyDescriptor> properties;
	
	private Set<Relationship> relationships;
	
	private final String arrayFlag="true";
	
	/**
	 * json路径.
	 */
	public static final PropertyDescriptor JSON_PATH = new PropertyDescriptor.Builder()
			.name("Json Path")
			.required(true)
			.description("json path value,such as:$.test")
			.addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
			.build();
	
	/**
	 * json路径.
	 */
	public static final PropertyDescriptor ARRAY_FLAG = new PropertyDescriptor.Builder()
			.name("Array Flag")
			.required(true)
			.description("mark if the input json is array or not")
			.addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
			.allowableValues("true", "false")
			.defaultValue("false")
			.build();
	
	/**
	 * 成功标识.
	 */
	public static final Relationship SUCCESS = new Relationship.Builder()
			.name("SUCCESS")
			.description("Succes relationship")
			.build();
	
	
	@Override
	public Set<Relationship> getRelationships(){
		return relationships;
	}
	
	@Override
	public List<PropertyDescriptor> getSupportedPropertyDescriptors(){
		return properties;
	}
	
	/**
	 * 初始化配置
	 * @param context
	 */
	@Override
	public void init(final ProcessorInitializationContext context){
		List<PropertyDescriptor> properties = new ArrayList<>();
		properties.add(JSON_PATH);
		properties.add(ARRAY_FLAG);
		this.properties = Collections.unmodifiableList(properties);
		
		Set<Relationship> relationships = new HashSet<>();
		relationships.add(SUCCESS);
		this.relationships = Collections.unmodifiableSet(relationships);
	}
	
	@Override
	public void onTrigger(final ProcessContext context, final ProcessSession session) throws ProcessException {
		final AtomicReference<String> value = new AtomicReference<>();
		FlowFile flowfile = session.get();
		session.read(flowfile, new InputStreamCallback() {
			@Override
			public void process(InputStream in) throws IOException {
				try{
					String json =IOUtils.toString(in, StandardCharsets.UTF_8.name());
					String flag = context.getProperty(ARRAY_FLAG).getValue();
					if (flag.equalsIgnoreCase(arrayFlag)){
						List<Object> dataList = JsonPath.read(json, context.getProperty(JSON_PATH).getValue());
						if (ObjectUtils.allNotNull(dataList)){
							StringBuilder all = new StringBuilder("[");
							int total = 0;
							for (Object object : dataList) {
								LinkedHashMap<String,Object> dataMap = (LinkedHashMap<String, Object>) object;
								Set<String> keys = dataMap.keySet();
								int count = 0;
								StringBuilder builder = new StringBuilder("{");
								for (String key :keys ) {
									if (count==keys.size()-1){
										builder.append("\""+key+"\":\""+dataMap.get(key)+"\"");
									}else{
										builder.append("\""+key+"\":\""+dataMap.get(key)+"\",");
									}
									count++;
								}
								if (total==dataList.size()-1){
									builder.append("}");
								}else {
									builder.append("},");
								}
								total++;
								all.append(builder.toString());
								builder.reverse();
							}
							all.append("]");
							value.set(all.toString());
						}
					}else {
						String result = JsonPath.read(json, context.getProperty(JSON_PATH).getValue());
						value.set(result);
					}
				}catch(Exception ex){
					ex.printStackTrace();
					getLogger().error("failed to read json string.");
				}
			}
		});
		
		//Write the results to an attribute
		String results = value.get();
		if(results != null && !results.isEmpty()){
			String flag = context.getProperty(ARRAY_FLAG).getValue();
			if (flag.equalsIgnoreCase(arrayFlag)){
				Map<String,String> data=new HashMap<>(16);
				data.put(NiFiConstant.MATCH_ATTR,value.toString());
				flowfile = session.putAllAttributes(flowfile,data);
			}else {
				flowfile = session.putAttribute(flowfile, NiFiConstant.MATCH_ATTR, results);
			}
		}
		
		//To write the results back out ot flow file
		flowfile = session.write(flowfile, new OutputStreamCallback() {
			@Override
			public void process(OutputStream out) throws IOException {
				out.write(value.get().getBytes());
			}
		});
		
		session.transfer(flowfile, SUCCESS);
	}

}
