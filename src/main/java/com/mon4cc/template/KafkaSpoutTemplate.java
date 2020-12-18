package com.mon4cc.template;

import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

import org.junit.Test;

public class KafkaSpoutTemplate {
	/**
	 * The template for package.
	 * {projectName} is need replaced
	 */
	String packageName = "com.mon4cc.{projectName}\n"
			+ "\n" ;
	
	/**
	 * The template for import.
	 */
	String importPackage = "import java.util.*;\n"
			+ "import org.apache.storm.*;\n"
			+ "import org.slf4j.*;\n"
			+ "import org.apache.kafka.clients.*;\n"
			+ "import log.*\n"
			+ "\n" ;
	/**
	 * spout's open method.
	 */
	String open = "\t  @Override\n"
			+ "\t  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector){\n"
			+ "\t\t {conf}\n"
			+ "\t\t kafkaInit(); \n"
			+ "\t\t  \n"
			+ "\t  } \n"
			+ "\n" ;
	/**
	 * spout's nextTuple method
	 */
	
	String nextTuple = "\t  @Override\n"
			+ "\t  public void nextTuple() {\n"
			+ "\t\t  {nextTuple}\n"
			+ "\t\t  \n"
			+ "\t  }\n" ;
	/**
	 * spout's declareOutputFields method
	 */
	String declareOutputFields = "\t  @Override\n"
			+ "\t  public void declareOutputFields(OutputFieldsDeclarer declarer) {\n"
			+ "\t\t  {declare}\n"
			+ "\t\t  \n"
			+ "\t  }\n" ;
	/*
	 * 
     */

	String bootstrapServers = "{bootstrap.servers}" ;
	String maxPollRecords = "{max.poll.records}" ;
	String autoCommit = "{enable.auto.commit}" ;
	String groupId = "{group.id}" ;
	String autoOffsetReset = "{auto.offset.reset}" ;
	String topic = "{topic}" ;
	
	String kafkaInit = "\t  private void kafkaInit() {\n"
			+ "\t\t  Properties props = new Properties(); \n"
			+ "\t\t  props.put(\"bootstrap.servers\"" + "," + bootstrapServers + ");\n"
			+ "\t\t  props.put(\"max.poll.records\"" + "," + maxPollRecords + ");\n"
			+ "\t\t  props.put(\"enable.auto.commit\"" + "," + autoCommit + ");\n"
			+ "\t\t  props.put(\"group.id\"" + "," + groupId + ");\n"
			+ "\t\t  props.put(\"auto.offset.reset\"" + "," +autoOffsetReset +");\n"
			+ "\t\t  props.put(\"key.deserializer\", \"org.apache.kafka.common.serialization.StringDeserializer\");\n"
			+ "\t\t  props.put(\"value.deserializer\", \"org.apache.kafka.common.serialization.StringDeserializer\");\n"
			+ "\t\t  consumer = new KafkaConsumer<String, String>(props);\n"
			+ "\t\t  this.consumer.subscribe(Arrays.asList("+topic+");\n"
					+ "\t  }";
	/**
	 * 
	 */
	String classGenerate="public class {className} extends BaseRichSpout {\n"
			+ "\t private KafkaConsumer<String, String> consumer;\r\n"
			+ "\t //msgList is used to received messege from kafka consumer\r\n" 
			+ "\t private ConsumerRecords<String, String> msgList;\r\n"
			+ "\n"
			+ open()
			+ "\n"
			+ nextTuple()
			+ "\n"
			+ declareOutputFields()
			+ kafkaInit
			+"\n"
			+ "}\n" ;
	
	
	@Test
	public void test() {
		System.out.println(
				packageName.replace("{projectName}", "template")+importPackage+
				classGenerate.replace("{className}", "Spout")) ;
		
	}
	
	/*
	 * select global configuration from database
	 */
	public String globleConfiguration() {
		
		return "" ;
	}
	
	/*
	 * select bootstrap.servers from database
	 */
	public String bootstrapServers() {
		return bootstrapServers.replace("{bootstrap.servers}", "database") ;
	}
	
	/*
	 * select max.poll.records from database
	 */
	public String maxPollRecords() {
		return maxPollRecords.replace("{max.poll.records}", "database") ;
	}
	
	public String autoCommit() {
		return autoCommit.replace("{enable.auto.commit}", "database") ;
	}
	public String groupId() {
		return groupId.replace("{group.id}", "database") ;
	}
	public String autoOffsetReset() {
		return autoOffsetReset.replace("{auto.offset.reset}","database") ;
	}
	
	public String topic() {
		return topic.replace("{topic}", "database") ;
	}
	/*
	 * select open method content from database
	 */
	public String open() {
		
		return open.replace("{conf}", "database")  ;
		
	}
	
	/*
	 * select nextTuple method content from database
	 */
	public String nextTuple() {
		return nextTuple.replace("{nextTuple}", "database") ;
	}
	
	/*
	 * select declareOutputFields method content from database
	 */
	public String declareOutputFields() {
		return declareOutputFields.replace("{declare}", "database") ;
	}
	
	
	public void generateSpout() {
		String  boltCode= "_execute.emit() ;"
				+ "{log}" ;
		boltCode.replace("{log}", "LOG.error(\"{}\", EventFactory.newSEmit(\"SP_Rand\", tid, mid, this.sid));");
		
	}
}
