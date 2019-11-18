package com.application.base.kafka.test;

import com.alibaba.fastjson.JSON;
import com.application.base.kafka.test.bean.Result;
import com.application.base.kafka.test.util.DataUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : 孤狼
 * @NAME: NifiDataProducer
 * @DESC: kafka生产者.
 **/
public class NifiDataProducer {
	
	private static KafkaProducer<String, String> producer;
	private static String topic;
	
	
	static ThreadFactory nameFactory =new ThreadFactoryBuilder().setNameFormat("pull-api-pool-%d").build();
	static int corePoolSize = Runtime.getRuntime().availableProcessors();
	/**
	 * 线程池:2000条数,放置最大2500个队列里
	 */
	static ExecutorService executor = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10L, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>(500),nameFactory,new ThreadPoolExecutor.AbortPolicy());
	
	/**
	 * 测试.
	 * @param args
	 */
	public static void main(String args[]) {
		String topic = "kafka-data";
		
		init(topic);
		
		int count = 5;
		
		// NIFI 的隐藏的 BUG.
		//testError(count);
		
		testOK(count);
	}
	
	/**
	 * 初始化操作.
	 * @param count
	 */
	public static void testOK(int count) {
		Result result = null;
		for (int i = 0; i <count ; i++) {
			Map<String,Object> teacher = new HashMap<>();
			teacher.put("teacher",DataUtil.getTeachers());
			result = Result.success(teacher);
			//30
			executor.submit(new ExecuteTask(topic,"teacher", JSON.toJSON(result).toString(),producer));
		}
		for (int i = 0; i <count ; i++) {
			Map<String,Object> student = new HashMap<>();
			student.put("student",DataUtil.getStudents());
			result = Result.success(student);
			//30
			executor.submit(new ExecuteTask(topic,"student",JSON.toJSON(result).toString(),producer));
		}
		for (int i = 0; i <count ; i++) {
			Map<String,Object> score = new HashMap<>();
			score.put("score",DataUtil.getScores());
			result = Result.success(score);
			//50
			executor.submit(new ExecuteTask(topic,"score",JSON.toJSON(result).toString(),producer));
		}
		for (int i = 0; i <count ; i++) {
			Map<String,Object> logistics = new HashMap<>();
			logistics.put("logistics",DataUtil.getLogisticss());
			result = Result.success(logistics);
			//50
			executor.submit(new ExecuteTask(topic,"logistics",JSON.toJSON(result).toString(),producer));
		}
		if (executor.isTerminated()){
			System.out.println("线程执行完毕,关闭连接!");
			producer.close();
			executor.shutdown();
			System.exit(0);
		}
	}
	
	/**
	 * 初始化操作.
	 * @param count
	 */
	public static void testError(int count) {
		Result result = null;
		for (int i = 0; i <count ; i++) {
			result = Result.success(DataUtil.getTeachers());
			//30
			executor.submit(new ExecuteTask(topic,"teacher", JSON.toJSON(result).toString(),producer));
		}
		for (int i = 0; i <count ; i++) {
			result = Result.success(DataUtil.getStudents());
			//30
			executor.submit(new ExecuteTask(topic,"student",JSON.toJSON(result).toString(),producer));
		}
		for (int i = 0; i <count ; i++) {
			result = Result.success(DataUtil.getScores());
			//50
			executor.submit(new ExecuteTask(topic,"score",JSON.toJSON(result).toString(),producer));
		}
		for (int i = 0; i <count ; i++) {
			result = Result.success(DataUtil.getLogisticss());
			//50
			executor.submit(new ExecuteTask(topic,"logistics",JSON.toJSON(result).toString(),producer));
		}
		if (executor.isTerminated()){
			System.out.println("线程执行完毕,关闭连接!");
			producer.close();
			executor.shutdown();
			System.exit(0);
		}
	}
	
	/**
	 * 初始化操作.
	 * @param topicName
	 */
	public static void init(String topicName) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.10.215:9092,192.168.10.216:9092,192.168.10.217:9092");
		props.put("acks", "all");
		props.put("retries", 5);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", StringSerializer.class.getName());
		props.put("value.serializer", StringSerializer.class.getName());
		producer = new KafkaProducer<String, String>(props);
		topic = topicName;
	}
	
}
