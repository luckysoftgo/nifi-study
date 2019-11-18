package com.application.base.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author : 孤狼
 * @NAME: NifiDataConsumer
 * @DESC: kafka 消费者
 **/
public class KafkaDataConsumer implements Runnable {
	private final KafkaConsumer<String, String> consumer;
	private ConsumerRecords<String, String> msgList;
	private final String topic;
	private static final String GROUPID = "groupA";
	
	/**
	 * 测试.
	 * @param args
	 */
	public static void main(String args[]) {
		KafkaDataConsumer test1 = new KafkaDataConsumer("kafka-data");
		Thread thread1 = new Thread(test1);
		thread1.start();
	}
	
	public KafkaDataConsumer(String topicName) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.10.215:9092,192.168.10.216:9092,192.168.10.217:9092");
		props.put("group.id", GROUPID);
		props.put("enable.auto.commit", "true");
		//想要读取之前的数据，必须加上
		props.put("auto.offset.reset", "earliest");
		/* 自动确认offset的时间间隔 */
		props.put("auto.commit.interval.ms", "1000");
		/*
		 * 一旦consumer和kakfa集群建立连接，
		 * consumer会以心跳的方式来高速集群自己还活着，
		 * 如果session.timeout.ms 内心跳未到达服务器，服务器认为心跳丢失，会做rebalence
		 */
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", StringDeserializer.class.getName());
		props.put("value.deserializer", StringDeserializer.class.getName());
		this.consumer = new KafkaConsumer<String, String>(props);
		this.topic = topicName;
		this.consumer.subscribe(Arrays.asList(topic));
	}
	
	@Override
	public void run() {
		int messageNo = 1;
		System.out.println("---------开始消费---------");
		try {
			for (;;) {
				msgList = consumer.poll(100);
				if(null!=msgList && msgList.count()>0){
					for (ConsumerRecord<String, String> record : msgList) {
						//消费100条就打印 ,但打印的数据不一定是这个规律的
						System.out.println(messageNo+"=======receive: key = " + record.key() + ", value = " + record.value()+" offset==="+record.offset());
						//当消费了1000条就退出
						if(messageNo%1000==0){
							break;
						}
						messageNo++;
					}
				}else{
					Thread.sleep(1000);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			consumer.close();
		}
	}
	
}
