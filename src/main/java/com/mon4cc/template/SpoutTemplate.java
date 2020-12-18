package com.mon4cc.template;


import java.util.Map;
import java.util.Random;

import org.junit.Test;






public class SpoutTemplate {
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
			+ "import log.*\n"
			+ "\n" ;
	/**
	 * spout's open method.
	 */
	String open = "\t  @Override\n"
			+ "\t  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector){\n"
			+ "\t\t  {conf}\n"
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
	/**
	 * 
	 */
	String classGenerate="public class {className} extends BaseRichSpout {\n"
			+ "\n"
			+ open()
			+ "\n"
			+ nextTuple()
			+ "\n"
			+ declareOutputFields()
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
