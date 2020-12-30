package com.mon4cc.template;

import com.mon4cc.entity.Bolt;
import com.mon4cc.entity.Flow;
import com.mon4cc.service.IBoltService;
import com.mon4cc.service.IFlowService;
import com.mon4cc.service.ITopologyconfigurationService;
import lombok.Data;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 * @auton xjyou_
 * BoltTemplate work for generate bolt code
 */
@Component
@Data
public class BoltTemplate {

	private String topologyId ;
	private Bolt bolt ;
	private Flow flow  ;
	private String topologyName ;

	public BoltTemplate(){

	}

	private String BoltClassTextGenerator = ""
			+ "<packageDoc>"
			+ "<importDoc>"
			+ "<classDoc>"
			+ "<attrDoc>"
			+ "<prepare>"
			+ "<execute>"
			+ "<declare>"
			+ "<end>";
	/*
	public String generateClassText(String topologyId, String topologyNameBolt,Bolt bolt, Flow flow){
		return BoltClassTextGenerator.replace("<packageDoc>",packageName(topologyName))
				.replace("<importDoc>", importPackage)
				.replace("<classDoc>",);
	}
	 */

	/**
	 * The template for package.
	 * {projectName} is need replaced
	 */
	private String packageName = "com.mon4cc.{projectName}\n"
			+ "\n" ;
	
	/**
	 * The template for import.
	 */
	private String importPackage = "import java.util.*;\n"
			+ "import org.apache.storm.*;\n"
			+ "import org.slf4j.*;\n"
			+ "import log.*\n"
			+ "\n" ;

	/**
	 * The template for method(#prepare)
	 * {conf} is need replaced
	 */
	private String prepare = "\t  @Override\n"
			+ "\t  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector){\n"
			+ "\t\t  collector = collector"
			+ "\t\t  {conf}\n"
			+ "\t\t  \n"
			+ "\t  } \n";
	/**
	 * The template for method(#execute)
	 * {execute} is need replaced
	 */
	private String execute = "\t  @Override\n"
			+ "\t  public void execute(Tuple input) {\n"
			+ "\t\t   tid = TID.next() ;\n"
			+ "\t\t  {execute}\n"
			+ "\t\t  \n"
			+ "\t  }\n" ;
	/**
	 * The template for method(#declareOutputFields)
	 * {declare} is need replaced
	 */
	private String declareOutputFields = "\t  @Override\n"
			+ "\t  public void declareOutputFields(OutputFieldsDeclarer declarer) {\n"
			+ "\t\t  {declare}\n"
			+ "\t\t  \n"
			+ "\t  }\n" ;

	private String log = "private static final Logger logger = LogManager.getLogger(<className>.class) ;" ;
	private String logEmit = "\t\t  logger.error(\"{}\", EventFactory.newEmit(<en>, TID.next(),input.getString(2), " +
			"<sid>"+ ")) ;" ;

	private String logStart = "\t\t logger.error(\"{}\", EventFactory.newTake(<en>,input.getString(1),input.getString(2), input.getString(3))) ;" ;

	private String logAck = "\t\t  logger.error(\"{}\", EventFactory.newAck(<en>,input.getString(1),input.getString(2), " +
			"<sid>"+")) ;" ;
	private String globleConfiguration = "\t OutputCollector collector ;"
			+"\t <globleConfiguration>" ;

	private String classMainGenerate="public class {className} extends BaseRichSpout {\n"
			+ "String id ; \n"
			+ "String tid = null ;\n"
			+ "\n"
			+ log
			+ "\n"
			+ globleConfiguration
			+ "\n"
			+ prepare
			+ "\n"
			+ execute
			+ "\n"
			+ declareOutputFields
			+ "}\n" ;

	public String classGenerate(){
		StringBuilder sb = new StringBuilder() ;
		sb.append(packageName(topologyName)) ;
		sb.append(importPackage) ;
		sb.append(classMainGenerate()) ;
//		return iBoltService.updateCode(bolt.getId(),sb.toString()) ;
		return sb.toString() ;
	}


	public String classMainGenerate(){
		return  this.classMainGenerate.replace("{className}", bolt.getBoltComponentName()).replace("<className>",log())
				.replace("<globleConfiguration>",globleConfiguration()).replace("{conf}",prepare())
				.replace("{execute}",execute()).replace("{declare}",declareOutputFields());
	}


	public String packageName(String topologyName){
		return packageName.replace("{projectName}", topologyName ) ;
	}

	public String log(){
		return bolt.getBoltComponentName() ;
	}

	public String globleConfiguration(){
		String [] codes = getBoltSimpleCode() ;
		return codes[0] ;
	}
	
	public String prepare() {
		String [] codes = getBoltSimpleCode() ;
		String prepareCode = codes[1] ;
		return prepareCode ;
	}
	
	public String execute() {
		String [] codes = getBoltSimpleCode() ;
		String newEmit = codes[3] ;
		String emit = newEmit.replace(";","")+".add(tid,mid,stream)" ;
		String execteCode = codes[2].replace("<logstart>",logStart.replace("<en>",logStart()))+emit+codes[4].replace("<logmid>",logEmit
				.replace("<en>",logEmit()).replace("<sid>",outSid()))
				.replace("<logack>",logAck.replace("<en>" ,logAck()).replace("<sid>",inSid())) ;

		return execteCode;
	}

	public String logEmit(){
		return bolt.getBoltComponentName() ;
	}

	public String logStart(){
		return  bolt.getBoltComponentName() ;
	}

	public String logAck(){
		return  bolt.getBoltComponentName() ;
	}

	public String outSid(){
		return bolt.getBoltStream() ;
	}

	public String inSid(){
		String inSid = "" ;
		if(flow.getTargetComponent().equals(bolt.getId())) inSid = flow.getStream() ;
		return inSid  ;
	}
	
	
	public String declareOutputFields() {
		return generateDeclareMethodContent() ;
	}

	public  String[] getBoltSimpleCode(){
		return bolt.getBoltCodeSimple().split("-") ;
	}

	public String generateDeclareMethodContent(){
		String stream = bolt.getBoltStream() ;
		String field = bolt.getBoltComponentName() ;
		// declarer.declareStream("S2", new Fields("word","tid", "mid","sid"));
		String content = "declarer.declareStream("+stream+", new Fields("+field+",\"tid\", \"mid\",\"sid\""+"))" ;
		return content;
	}


}
