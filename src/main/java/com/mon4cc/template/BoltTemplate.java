package com.mon4cc.template;

import com.mon4cc.entity.Bolt;
import com.mon4cc.service.IBoltService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @auton xjyou_
 */

public class BoltTemplate {

	private String topologyId ;
	Bolt bolt = null;
	public BoltTemplate() {
	}

	public BoltTemplate(String topologyId) {
		this.topologyId = topologyId;
		bolt = iBoltService.selectBolt(topologyId) ;
	}

	@Autowired
	private IBoltService iBoltService ;

	
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

	String prepare = "\t  @Override\n"
			+ "\t  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector){\n"
			+ "\t\t  {conf}\n"
			+ "\t\t  \n"
			+ "\t  } \n"
			+ "\n" ;
	
	String execute = "\t  @Override\n"
			+ "\t  public void execute(Tuple input) {\n"
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
			+ globleConfiguration()
			+ "\n"
			+ prepare()
			+ "\n"
			+ execute()
			+ "\n"
			+ declareOutputFields()
			+ "}\n" ;
//	logger.error("{}", EventFactory.newEmit("BT_Split", TID.next(),input.getString(2), "S2")) ;
	String sid = "<sid>" ;

	String logEmit = "logger.error(\"{}\", EventFactory.newEmit(<en>, TID.next(),input.getString(2), " +
		sid()+ ")) ;" ;
	@Test
	public void test() {
		System.out.println(packageName.replace("{projectName}", "generatecode")+importPackage+
				classGenerate.replace("{className}", "Bolt")) ;
	}

	public String globleConfiguration(){
		String [] codes = getBoltSimpleCode() ;
		return codes[0] ;
	}
	
	public String prepare() {
		String [] codes = getBoltSimpleCode() ;
		String prepareCode = codes[1] ;
		return prepare.replace("{conf}",prepareCode) ;
	}
	
	public String execute() {
		String [] codes = getBoltSimpleCode() ;
		String newEmit = codes[3] ;
		String emit = newEmit+".add(tid,mid,stream)" ;
		String execteCode = codes[2]+emit+codes[4].replace("<logemit>",logEmit()) ;

		return execute.replace("{execute}", execteCode) ;
	}

	public String logEmit(){
		return logEmit.replace("en", bolt.getBoltComponentName()) ;
	}

	public String sid(){
		return sid.replace("<sid>", bolt.getBoltStream()) ;
	}
	
	
	public String declareOutputFields() {
		return declareOutputFields.replace("{declare}", generateDeclareMethodContent()) ;
	}

	public  String[] getBoltSimpleCode(){
		return bolt.getBoltCodeSimple().split("||") ;
	}

	public String generateDeclareMethodContent(){
		String stream = bolt.getBoltStream() ;
		String field = bolt.getBoltComponentName() ;
		// declarer.declareStream("S2", new Fields("word","tid", "mid","sid"));
		String content = "declarer.declareStream("+stream+", new Fields("+field+",\"tid\", \"mid\",\"sid\""+"))" ;
		return content;
	}



}
