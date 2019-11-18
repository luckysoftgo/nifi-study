package com.application.base.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author : 孤狼
 * @NAME: NifiDataProducer
 * @DESC: kafka生产者.
 **/
public class KafkaDataProducer implements Runnable{
	
	private final KafkaProducer<String, String> producer;
	private final String topic;
	
	/**
	 * 测试.
	 * @param args
	 */
	public static void main(String args[]) {
		KafkaDataProducer test = new KafkaDataProducer("kafka-data");
		Thread thread = new Thread(test);
		thread.start();
	}
	
	public KafkaDataProducer(String topicName) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.10.215:9092,192.168.10.216:9092,192.168.10.217:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", StringSerializer.class.getName());
		props.put("value.serializer", StringSerializer.class.getName());
		this.producer = new KafkaProducer<String, String>(props);
		this.topic = topicName;
	}
	
	
	@Override
	public void run() {
		int messageNo = 1;
		try {
			for(;;) {
				String messageStr="你好，这是第"+messageNo+"条数据";
				producer.send(new ProducerRecord<String, String>(topic, "Message", messageStr));
				//生产了100条就打印
				if(messageNo%100==0){
					System.out.println("发送的信息:" + messageStr);
				}
				//生产1000条就退出
				if(messageNo%1000==0){
					System.out.println("成功发送了"+messageNo+"条");
					break;
				}
				messageNo++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			String json="{\"error_code\":0,\"reason\":\"ok\",\"result\":{\"items\":[{\"regStatus\":\"存续\"," +
					"\"estiblishTime\":1495555200000,\"regCapital\":\"20万元\",\"pencertileScore\":4902,\"type\":1," +
					"\"legalPersonName\":\"温旭颖\",\"toco\":2,\"legalPersonId\":2051255554," +
					"\"name\":\"陕西西部资信股份有限公司海南分公司\",\"logo\":\"https://img5.tianyancha" +
					".com/logo/lll/f4155abb4babbc1985049529e103779a.png@!f_200x200\",\"alias\":\"西部资信\"," +
					"\"id\":3053414776,\"category\":\"723\",\"personType\":1,\"base\":\"han\"},{\"regStatus\":\"注销\"," +
					"\"estiblishTime\":1473264000000,\"regCapital\":\"30万元\",\"pencertileScore\":3860,\"type\":1," +
					"\"legalPersonName\":\"常青\",\"toco\":8,\"legalPersonId\":1911055314," +
					"\"name\":\"陕西西部资信股份有限公司新疆分公司\",\"logo\":\"https://img5.tianyancha" +
					".com/logo/lll/13aab1bd91ed711458486b096ea044eb.png@!f_200x200\",\"alias\":\"西部资信\"," +
					"\"id\":2958332903,\"category\":\"721\",\"personType\":1,\"base\":\"xj\"},{\"regStatus\":\"存续\"," +
					"\"estiblishTime\":1469548800000,\"regCapital\":\"40万元\",\"pencertileScore\":3860,\"type\":1," +
					"\"legalPersonName\":\"温旭颖\",\"toco\":2,\"legalPersonId\":2051255554," +
					"\"name\":\"陕西西部资信股份有限公司青海分公司\",\"logo\":\"https://img5.tianyancha" +
					".com/logo/lll/b687a047c912515c59ef6a9247eff5c0.png@!f_200x200\",\"alias\":\"西部资信\"," +
					"\"id\":2820726982,\"category\":\"723\",\"personType\":1,\"base\":\"qh\"},{\"regStatus\":\"存续\"," +
					"\"estiblishTime\":1467648000000,\"regCapital\":\"0万元\",\"pencertileScore\":3860,\"type\":1," +
					"\"legalPersonName\":\"常青\",\"toco\":1,\"legalPersonId\":1911055314,\"name\":\"陕西西部资信股份有限公司重庆公司\"," +
					"\"alias\":\"西部资信\",\"id\":2353678400,\"category\":\"659\",\"personType\":1,\"base\":\"cq\"}," +
					"{\"regStatus\":\"注销\",\"estiblishTime\":1467648000000,\"regCapital\":\"50万元\"," +
					"\"pencertileScore\":3860,\"type\":1,\"legalPersonName\":\"常青\",\"toco\":8," +
					"\"legalPersonId\":1911055314,\"name\":\"陕西西部资信股份有限公司重庆分公司\",\"logo\":\"https://img5.tianyancha" +
					".com/logo/lll/6bd78357e8b4248c7be315a1bc29b24c.png@!f_200x200\",\"alias\":\"西部资信\"," +
					"\"id\":2354581707,\"category\":\"659\",\"personType\":1,\"base\":\"cq\"},{\"regStatus\":\"存续\"," +
					"\"estiblishTime\":1465920000000,\"regCapital\":\"60万元\",\"pencertileScore\":3860,\"type\":1," +
					"\"legalPersonName\":\"闫小明\",\"toco\":7,\"legalPersonId\":2238195558," +
					"\"name\":\"陕西西部资信股份有限公司四川分公司\",\"logo\":\"https://img5.tianyancha" +
					".com/logo/lll/99d7617f5bd533245cdc405910c7634a.png@!f_200x200\",\"alias\":\"西部资信\"," +
					"\"id\":2412277124,\"category\":\"729\",\"personType\":1,\"base\":\"sc\"},{\"regStatus\":\"存续\"," +
					"\"estiblishTime\":1442246400000,\"regCapital\":\"70万元\",\"pencertileScore\":2797,\"type\":1," +
					"\"legalPersonName\":\"闫小明\",\"toco\":1,\"legalPersonId\":2238195558,\"name\":\"陕西西部资信有限公司河北分公司\"," +
					"\"logo\":\"https://img5.tianyancha.com/logo/lll/98e22bd0ecac6ee570b948db3d67ee68" +
					".png@!f_200x200\",\"alias\":\"西部资信\",\"id\":1001817082,\"category\":\"723\",\"personType\":1," +
					"\"base\":\"heb\"},{\"regStatus\":\"存续\",\"estiblishTime\":1442246400000,\"regCapital\":\"50万元\"," +
					"\"pencertileScore\":2797,\"type\":1,\"legalPersonName\":\"闫小明\",\"toco\":7," +
					"\"legalPersonId\":2238195558,\"name\":\"陕西西部资信股份有限公司河北分公司\",\"logo\":\"https://img5.tianyancha" +
					".com/logo/lll/d61bb60ec4d67e028a10eb82a94cc2e0.png@!f_200x200\",\"alias\":\"西部资信\"," +
					"\"id\":2411359469,\"category\":\"729\",\"personType\":1,\"base\":\"heb\"},{\"regStatus\":\"存续\"," +
					"\"estiblishTime\":1440518400000,\"pencertileScore\":2797,\"regCapital\":\"50万元\",\"type\":1," +
					"\"legalPersonName\":\"闫小明\",\"toco\":1,\"legalPersonId\":2238195558,\"name\":\"陕西西部资信有限公司山西分公司\"," +
					"\"logo\":\"https://img5.tianyancha.com/logo/lll/a558884b3a35b285b14a4b128ccb3572" +
					".png@!f_200x200\",\"alias\":\"西部资信\",\"id\":1399790151,\"category\":\"652\",\"personType\":1," +
					"\"base\":\"sx\"},{\"regStatus\":\"存续\",\"estiblishTime\":1440518400000,\"regCapital\":\"60万元\"," +
					"\"pencertileScore\":2797,\"type\":1,\"legalPersonName\":\"闫小明\",\"toco\":7," +
					"\"legalPersonId\":2238195558,\"name\":\"陕西西部资信股份有限公司山西分公司\",\"logo\":\"https://img5.tianyancha" +
					".com/logo/lll/6fe1697262e9536d070ede3e1feb6df8.png@!f_200x200\",\"alias\":\"西部资信\"," +
					"\"id\":2953658074,\"category\":\"759\",\"personType\":1,\"base\":\"sx\"},{\"regStatus\":\"存续\"," +
					"\"estiblishTime\":1435593600000,\"regCapital\":\"10万人民币元\",\"pencertileScore\":2797,\"type\":1," +
					"\"legalPersonName\":\"闫小明\",\"toco\":1,\"legalPersonId\":2238195558,\"name\":\"陕西西部资信有限公司贵州分公司\"," +
					"\"logo\":\"https://img5.tianyancha.com/logo/lll/20043cfb0c2849549eb5f489804032a2" +
					".png@!f_200x200\",\"alias\":\"西部资信\",\"id\":1506039879,\"category\":\"529\",\"personType\":1," +
					"\"base\":\"gz\"},{\"regStatus\":\"存续\",\"estiblishTime\":1435593600000," +
					"\"regCapital\":\"10万人民币元\",\"pencertileScore\":3333,\"type\":1,\"legalPersonName\":\"闫小明\"," +
					"\"toco\":7,\"legalPersonId\":2238195558,\"name\":\"陕西西部资信股份有限公司贵州分公司\",\"logo\":\"https://img5" +
					".tianyancha.com/logo/lll/30026f0a5394c98b6622dd427abf871b.png@!f_200x200\",\"alias\":\"陕西西部\"," +
					"\"id\":2402434792,\"category\":\"749\",\"personType\":1,\"base\":\"gz\"},{\"regStatus\":\"开业\"," +
					"\"estiblishTime\":1433952000000,\"regCapital\":\"10万人民币元\",\"pencertileScore\":3860,\"type\":1," +
					"\"legalPersonName\":\"常青\",\"toco\":1,\"legalPersonId\":1911055314,\"name\":\"陕西西部资信有限公司银川分公司\"," +
					"\"logo\":\"https://img5.tianyancha.com/logo/lll/baf9fd6f2659f93e54a8a92d9300b7c1" +
					".png@!f_200x200\",\"alias\":\"西部资信\",\"id\":2311747064,\"category\":\"723\",\"personType\":1," +
					"\"base\":\"nx\"},{\"regStatus\":\"存续\",\"estiblishTime\":1433952000000," +
					"\"regCapital\":\"10万人民币元\",\"pencertileScore\":3860,\"type\":1,\"legalPersonName\":\"朱军奇\"," +
					"\"toco\":1,\"legalPersonId\":1972040953,\"name\":\"陕西西部资信股份有限公司银川分公司\",\"logo\":\"https://img5" +
					".tianyancha.com/logo/lll/63047a9c140b6565b90f3f0b8d87a751.png@!f_200x200\",\"alias\":\"西部资信\"," +
					"\"id\":2353114548,\"category\":\"659\",\"personType\":1,\"base\":\"nx\"},{\"regStatus\":\"存续\"," +
					"\"estiblishTime\":1417622400000,\"pencertileScore\":4717,\"regCapital\":\"10万人民币元\",\"type\":1," +
					"\"legalPersonName\":\"郑志杰\",\"toco\":1,\"legalPersonId\":2217329527," +
					"\"name\":\"陕西西部资信有限公司内蒙古分公司\",\"logo\":\"https://img5.tianyancha" +
					".com/logo/lll/f7267913f67b6bc273372b9ff70eac6e.png@!f_200x200\",\"alias\":\"西部资信\"," +
					"\"id\":1099681391,\"category\":\"723\",\"personType\":1,\"base\":\"nmg\"},{\"regStatus\":\"存续\"," +
					"\"estiblishTime\":1417622400000,\"regCapital\":\"10万人民币元\",\"pencertileScore\":3957,\"type\":1," +
					"\"legalPersonName\":\"郑志杰\",\"toco\":1,\"legalPersonId\":2217329527," +
					"\"name\":\"陕西西部资信股份有限公司内蒙古分公司\",\"logo\":\"https://img5.tianyancha" +
					".com/logo/lll/2d8ba6820fc6546405199fd17d8c7983.png@!f_200x200\",\"alias\":\"西部资信\"," +
					"\"id\":2399909764,\"category\":\"723\",\"personType\":1,\"base\":\"nmg\"},{\"regStatus\":\"存续\"," +
					"\"estiblishTime\":1412006400000,\"pencertileScore\":2381,\"regCapital\":\"10万人民币元\",\"type\":1," +
					"\"legalPersonName\":\"侯艳军\",\"toco\":1,\"legalPersonId\":1789356756,\"name\":\"陕西西部资信有限公司湖北分公司\"," +
					"\"logo\":\"https://img5.tianyancha.com/logo/lll/298f82edf282bc5096054c0cef24fd50" +
					".png@!f_200x200\",\"alias\":\"西部资信\",\"id\":1322384391,\"category\":\"723\",\"personType\":1," +
					"\"base\":\"hub\"},{\"regStatus\":\"注销\",\"estiblishTime\":1412006400000," +
					"\"regCapital\":\"10万人民币元\",\"pencertileScore\":2381,\"type\":1,\"legalPersonName\":\"侯艳军\"," +
					"\"toco\":4,\"legalPersonId\":1789356756,\"name\":\"陕西西部资信股份有限公司湖北分公司\",\"logo\":\"https://img5" +
					".tianyancha.com/logo/lll/a33e025d4354e18c1324fcee9480ed02.png@!f_200x200\",\"alias\":\"西部资信\"," +
					"\"id\":2887360582,\"category\":\"723\",\"personType\":1,\"base\":\"hub\"}],\"total\":18}}";
			producer.send(new ProducerRecord<String, String>(topic, "Message", json));
			producer.close();
		}
	}
	
}
