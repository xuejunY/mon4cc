package com.mon4cc.template;

import org.junit.Test;



public class BoltTemplate {
	
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
	/*
	 * public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		// TODO Auto-generated method stub
		_collector=collector;
	}
	 */
	String prepare = "\t  @Override\n"
			+ "\t  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector){\n"
			+ "\t\t  {conf}\n"
			+ "\t\t  \n"
			+ "\t  } \n"
			+ "\n" ;
	
	String execute = "\t  @Override\n"
			+ "\t  public void execute() {\n"
			+ "\t\t  {execute}\n"
			+ "\t\t  \n"
			+ "\t  }\n" ;
	
	String declareOutputFields = "\t  @Override\n"
			+ "\t  public void declareOutputFields(OutputFieldsDeclarer declarer) {\n"
			+ "\t\t  {declare}\n"
			+ "\t\t  \n"
			+ "\t  }\n" ;
	
	String classGenerate="public class {className} extends BaseRichSpout {\n"
			+ "\n"
			+ prepare()
			+ "\n"
			+ execute()
			+ "\n"
			+ declareOutputFields()
			+ "}\n" ;
	@Test
	public void test() {
		System.out.println(packageName.replace("{projectName}", "generatecode")+importPackage+
				classGenerate.replace("{className}", "Bolt")) ;
	}
	
	public String prepare() {
		return prepare.replace("{conf}","database") ;
	}
	
	public String execute() {
		return execute.replace("{execute}", "database") ;
	}
	
	
	
	public String declareOutputFields() {
		return declareOutputFields.replace("{declare}", "database") ;
	}
	

}
