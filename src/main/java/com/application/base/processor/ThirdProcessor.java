package com.application.base.processor;

import com.application.base.cont.NiFiConstant;
import org.apache.commons.io.IOUtils;
import org.apache.nifi.annotation.behavior.ReadsAttribute;
import org.apache.nifi.annotation.behavior.ReadsAttributes;
import org.apache.nifi.annotation.behavior.SideEffectFree;
import org.apache.nifi.annotation.behavior.WritesAttribute;
import org.apache.nifi.annotation.behavior.WritesAttributes;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.SeeAlso;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : 孤狼
 * @NAME: ThirdProcessor
 * @DESC: 给文件加头信息.
 **/
@Tags({"third-example:deal with content!"})
@SeeAlso({})
@SideEffectFree
@CapabilityDescription("add prefix to given content.")
@ReadsAttributes({@ReadsAttribute(attribute="", description="")})
@WritesAttributes({@WritesAttribute(attribute="", description="")})
public class ThirdProcessor extends AbstractProcessor {
	
	/**
	 * 属性描述对象集合
	 */
	private List<PropertyDescriptor> descriptors;
	/**
	 * 关联关系集合
	 */
	private Set<Relationship> relationships;
	/**
	 * 文件设置.
	 */
	private static final String FILE_NAME = "combine-";
	private static final String FILE_SUFFIX = ".txt";
	
	public static final PropertyDescriptor ABSOLUTE_PATH = new PropertyDescriptor.Builder()
			.name("ABSOLUTE_PATH")
			.displayName("ABSOLUT PATH")
			.description("input file path for operating")
			.required(true)
			//非空验证
			.addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
			.build();
	
	public static final Relationship SHIP_SUCCESS = new Relationship.Builder()
			.name("sucess")
			.description("example relationship uccess")
			.build();
	
	public static final Relationship SHIP_FAILURE = new Relationship.Builder()
			.name("failure")
			.description("example relationship failure")
			.build();
	
	public static final PropertyDescriptor CHARSET = new PropertyDescriptor.Builder()
			.name("character-set")
			.displayName("Character Set")
			.required(true)
			.defaultValue("UTF-8")
			.addValidator(StandardValidators.CHARACTER_SET_VALIDATOR)
			.build();
	
	@Override
	protected void init(final ProcessorInitializationContext context) {
		final List<PropertyDescriptor> descriptors = new ArrayList<PropertyDescriptor>();
		descriptors.add(ABSOLUTE_PATH);
		descriptors.add(CHARSET);
		this.descriptors = Collections.unmodifiableList(descriptors);
		
		final Set<Relationship> ships = new HashSet<Relationship>();
		ships.add(SHIP_SUCCESS);
		ships.add(SHIP_FAILURE);
		this.relationships = Collections.unmodifiableSet(ships);
	}
	
	@Override
	public Set<Relationship> getRelationships() {
		return this.relationships;
	}
	
	@Override
	public final List<PropertyDescriptor> getSupportedPropertyDescriptors() {
		return descriptors;
	}
	
	@OnScheduled
	public void onScheduled(final ProcessContext context) {
		getLogger().info("Processor-Name"+context.getName());
		Map<PropertyDescriptor, String> dataMap = context.getProperties();
		for (Map.Entry<PropertyDescriptor, String> entry : dataMap.entrySet()) {
			getLogger().info("key="+entry.getKey().toString()+",value="+entry.getValue());
		}
	}
	
	@Override
	public void onTrigger(final ProcessContext context, final ProcessSession session) throws ProcessException {
		FlowFile flowFile = session.get();
		if ( flowFile == null ) {
			return;
		}
		final AtomicReference<String> value = new AtomicReference<>();
		//utf8 的设置
		final Charset charset = Charset.forName(context.getProperty(CHARSET).getValue());
		session.read(flowFile, new InputStreamCallback() {
			@Override
			public void process(InputStream inputStream) throws IOException {
				try{
					String headerDesc = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
					String filePath = context.getProperty(ABSOLUTE_PATH).getValue();
					InputStreamReader inRd = new InputStreamReader(new FileInputStream(filePath),charset);
					BufferedReader reader = new BufferedReader(inRd);
					String line=null;
					StringBuilder  builder = new StringBuilder();
					while (null!=(line=reader.readLine())){
						getLogger().info("文件信息是:"+line);
						builder.append(headerDesc+new String(line.getBytes(charset),StandardCharsets.UTF_8.name())+"\n\t");
					}
					//以 utf8 的方式把流信息写出去.
					getLogger().info("处理得到的结果是:"+builder.toString());
					value.set(builder.toString());
				}catch(Exception ex){
					ex.printStackTrace();
					getLogger().error("failed to read input string!");
				}
			}
		});
		String results = value.get();
		if(results != null && !results.isEmpty()){
			flowFile = session.putAttribute(flowFile, NiFiConstant.MATCH_ATTR, results);
		}
		
		//写入文件信息.
		flowFile = session.write(flowFile, new OutputStreamCallback() {
			@Override
			public void process(OutputStream outputStream) throws IOException {
				getLogger().info("写出的消息是:"+value.get());
				byte[] content = value.get().getBytes();
				//远程的输出流
				outputStream.write(content);
				
				//重新定义本地输出流.
				outputStream = new FileOutputStream(new File(FILE_NAME+uuid()+FILE_SUFFIX));
				outputStream.write(content);
			}
		});
		session.transfer(flowFile, SHIP_SUCCESS);
	}
	
	/**
	 * 产生一个32位的GUID
	 * @return
	 */
	public String uuid() {
		return getIdentifier().replace("-", "").toUpperCase();
	}
}
