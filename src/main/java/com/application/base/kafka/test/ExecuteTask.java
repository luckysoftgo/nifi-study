package com.application.base.kafka.test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author : 孤狼
 * @NAME: ExecuteTask
 * @DESC: 多线程跑任务.
 **/
public class ExecuteTask implements Callable<String> {

	/**
	 * 发送的 json 串
	 */
	private String json;
	/**
	 * 发送的 topic 串
	 */
	private String topic;
	/**
	 * 发送的 key
	 */
	private String msgKey;
	/**
	 * 发送者
	 */
	private final KafkaProducer<String, String> producer;
	
	/**
	 * 实例对象
	 * @param topic
	 * @param msgKey
	 * @param json
	 * @param producer
	 */
	public ExecuteTask(String topic,String msgKey,String json,KafkaProducer<String, String> producer){
		this.topic = topic;
		this.msgKey = msgKey;
		this.json=json;
		this.producer = producer;
	}
	
	@Override
	public String call() throws Exception {
		try {
			Future<RecordMetadata> task =  producer.send(new ProducerRecord<String, String>(topic, msgKey, json));
			RecordMetadata metadata = task.get();
			System.out.println("当前线程:"+Thread.currentThread().getName()+",topic:"+metadata.topic()+",partition:"+metadata.partition());
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
