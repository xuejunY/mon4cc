package com.mon4cc.template;

import com.mon4cc.entity.Bolt;
import com.mon4cc.entity.Flow;
import com.mon4cc.service.IBoltService;
import com.mon4cc.service.IFlowService;
import com.mon4cc.service.ITopologyconfigurationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

/**
 * @auton xjyou_
 * BoltTemplate work for generate bolt code
 */
@Component
public class BoltTemplate {

	private String topologyId ;
	Bolt bolt = null ;
	Flow flow = null ;
	public BoltTemplate() {
	}

	public BoltTemplate(String topologyId, Bolt bolt) {
		this.topologyId = topologyId;
		this.bolt = bolt ;
		//需要一个grouping id, topology id, bolt id
		//bolt名字；这种方法只适用于一个流进入bolt
		String inGroupingId = iFlowService.getFlowIdByTarget(bolt.getBoltComponentName()) ;
		flow = iFlowService.selectFlow(inGroupingId,topologyId) ;
	}

	@Autowired
	private ITopologyconfigurationService iTopologyconfigurationService ;

	@Autowired
	private IBoltService iBoltService;

	@Autowired
	private IFlowService iFlowService ;

	
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
	
	String classMainGenerate="public class {className} extends BaseRichSpout {\n"
			+ "\n"
			+ log()
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


	String logEmit = "\t\t  logger.error(\"{}\", EventFactory.newEmit(<en>, TID.next(),input.getString(2), " +
		outSid()+ ")) ;" ;

	String logStart = "\t\t logger.error(\"{}\", EventFactory.newTake(<en>,input.getString(1),input.getString(2), input.getString(3))) ;" ;

	String logAck = "\t\t  logger.error(\"{}\", EventFactory.newAck(<en>,input.getString(1),input.getString(2), " +
			inSid()+")) ;" ;

	public boolean classGenerate(){
		StringBuilder sb = new StringBuilder() ;
		sb.append(packageName(topologyId)) ;
		sb.append(importPackage) ;
		sb.append(classMainGenerate(bolt)) ;
		return iBoltService.updateCode(bolt.getId(),sb.toString()) ;
	}


	public String classMainGenerate(Bolt bolt){
		return classMainGenerate.replace("{className}", bolt.getBoltComponentName()) ;
	}


	public String packageName(String topologyId){
		return packageName.replace("{projectName}", iTopologyconfigurationService.getTopologyName(topologyId) ) ;
	}

	public String log(){
		String log = "private static final Logger logger = LogManager.getLogger(<className>.class) ;" ;
		return log.replace("<className>",bolt.getBoltComponentName()) ;
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
		String execteCode = codes[2].replace("<logstart>",logStart())+emit+codes[4].replace("<logemit>",logEmit())
				.replace("<logend>",logAck()) ;

		return execute.replace("{execute}", execteCode) ;
	}

	public String logEmit(){
		return logEmit.replace("en", bolt.getBoltComponentName()) ;
	}

	public String logStart(){
		return logStart.replace("en", bolt.getBoltComponentName()) ;
	}

	public String logAck(){
		return logAck.replace("en", bolt.getBoltComponentName()) ;
	}

	public String outSid(){
		return "<sid>".replace("<sid>", bolt.getBoltStream()) ;
	}

	public String inSid(){
		String inSid = "" ;
		if(flow.getTargetComponent().equals(bolt.getBoltComponentName())) inSid = flow.getStream() ;
		return "<sid>".replace("<sid>",inSid ) ;
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
