package com.mon4cc.template;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.mon4cc.parse.ModelParse;

public class ComfigurationTemplate {
	private static final Logger logger = LogManager.getLogger(ComfigurationTemplate.class) ;
	boolean isLocal = true ;
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
	
	String conf = "\t\t Config conf = new Config();\t\n" ;
	
	String mainMethod = "\t public static void main(String[] args) throws Exception {\t\n"
			+ "\t\t TopologyBuilder builder = new TopologyBuilder() ;\t\n"
			+ "\t\t {configuration} \t\n"
			+ "\t }" ;
	
	String classGene = "public class {topologyName} {\t\n"
			+ mainMethod
			+ "\n"
			+ "}" ;
	/**
	 * local mode
	 */
	String localMode =	"\t\t LocalCluster cluster = new LocalCluster() ; \t\n"	; 
	String submitTopology = "\t\t cluster.submitTopology(\"{topologyName}\", conf, builder.createTopology());\t\n" ;
	/**
	 * cluster mode
	 */
	String clustMode = "\t\t StormSubmitter.submitTopology(\"{topologyName}\", conf, builder.createTopology());\t\n" ;
	
	String spoutParallelism = "" ;
	String spoutGene = "\t\t builder.setSpout(\"{spoutName}\", new {spoutName}(), "+spoutParallelism+"); \t\n" ;

	String boltParallelism = "" ;
	String grouping = "" ;
	String outGoing = "" ;
	String stream =	"" ;
	String boltGene = "\t\t builder.setBolt(\"{boltName}\", new {boltName}(), "+boltParallelism+")"
			+ "."+grouping +"("+outGoing+","+stream+") ; \t\n" ;
	
	public String spoutGene() {
		return spoutGene.replace("{spoutName}", "database") ;
	}
	public String boltGene() {
		return boltGene.replace("{boltName}", "database") ;
	}
	public String config() {
		if(isLocal) {
			return localMode+submitTopology.replace("{topologyName}", "database");
		}else return clustMode.replace("{topologyName}", "database") ;
	}
	
	public String boltGenerateGrouping2() {
		String grouping1 = "" ;
		String grouping2 = "" ;
		String outGoing1 = "" ;
		String outGoing2 = "" ;
		String stream1 = "" ;
		String stream2 = "" ;
		String boltParallelism = "" ;
		String boltGene = "\t\t builder.setBolt(\"{boltName}\", new {boltName}(), "+boltParallelism+")"
				+ "."+grouping1 +"("+outGoing1+","+stream1+") "
				+ "."+grouping2 +"("+outGoing2+","+stream2+")) ; \t\n" ;
		return boltGene.replace("{boltName}", "database") ;
	}
	

	
	@Test
	public void test() {
	
		System.out.println( packageName+importPackage+classGene.replace("{main}",mainMethod.replace("{configuration}", 
				spoutGene()+boltGene()+config()))) ;
		
	}
	

	
}
