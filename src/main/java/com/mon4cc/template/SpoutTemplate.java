package com.mon4cc.template;


import java.util.Map;
import java.util.Random;

import com.mon4cc.entity.Spout;
import lombok.Data;
import org.junit.Test;
import org.springframework.stereotype.Component;

/**
 * The class is for generating Spout class text, based on topologyName, spoutName, etc.
 * <JsonEventClass> ::=
 * 		  <packageAndImportDec>
 * 		    <classDec>
 * 		    <attrsDecs>
 * 		    <open>
 * 		    <nextTuple>
 * 		    <declareOutputFields>
 * 		    <ack>
 * 		    <fail>
 * 			<end>
 *
 */

/**
 * @author xjyou_
 * @Date 2020.12.29
 */
@Component
@Data
public class SpoutTemplate {

	private Spout spout ;


	private String spoutClassStructure = ""
			+ " <packageAndImportDec>"
			+ " <classDec>"
			+ " <attrsDecs>"
			+ " <open>"
			+ " <nextTuple>"
			+ " <declareOutputFields>"
			+ " <ack>"
			+ " <fail>"
			+ " <end>" ;

	public String generateClassText(String topologyName, String spoutName){
		return spoutClassStructure.replace("<packageAndImportDec>", packageAndImportTemplate(topologyName))
				.replace("<classDec>", classDecTemplate(spoutName))
				.replace("<attrsDecs>", attrsDecsTemplate())
				.replace("<open>", openMethodTemplate())
				.replace(" <nextTuple>", nextTupleMethodTemplate(spoutName))
				.replace("<declareOutputFields>", declareOutputFieldsMethodTemplate())
				.replace("<ack>",ackMethodTemplate())
				.replace("<fail>", failMethodTemplate())
				.replace("<end>", "}") ;
	}

	/**
	 * The template for package and import.
	 * <projectName> is need replaced
	 */
	private String packageAndImportTemplate = "com.mon4cc.<projectName>\n"
			+ "import java.text.SimpleDateFormat;\n"
			+ "import java.util.*;\n"
			+ "import org.apache.storm.*;\n"
			+ "import org.slf4j.*;\n"
			+ "import log.*\n"
			+ "\n" ;

	public String packageAndImportTemplate(String topologyName){
		return packageAndImportTemplate.replace("<projectName>", topologyName) ;
	}



	/**
	 * The template for  class.
	 * <className> is need replaced
	 */
	private String classDecTemplate = "\t\t  public class <className> extends BaseRichSpout { \n"
			+ "\t\t  private static final Logger logger = LogManager.getLogger(<className>.class) ; \n"
			+ "\t\t  private static final IDGenerator MID = IDFactory.getIDGenerator(\"mid_\") ;\n"
			+ "\t\t  public static final IDGenerator TID = IDFactory.getIDGenerator(\"tid_\") ;\n"
			+ "\t\t  SpoutOutputCollector collector ;\n"
			+ "\t\t  String sid ;\n"
			+ "\t\t  String mid = null ;\n"
			+ "\t\t  String tid = null ;\n" ;

	public String classDecTemplate(String spoutName){
		return classDecTemplate.replace("<className>",spoutName) ;
	}

	/**
	 * The template for global attributes.
	 * <attrsDecs> is need replaced
	 */
	private String attrsDecsTemplate = "\t\t <attrsDecs> \n" ;


	public String attrsDecsTemplate(){
		return getUserCode()[0] ;
	}

	/**
	 * The template for method(#open)
	 * <initialization> is need replaced
	 */
	private String openMethodTemplate = "\t\t  @Override\n"
			+ "\t\t  public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {\n"
			+ "\t\t    collector = collector ;\n"
			+ "\t\t    <initialization>\n"
			+ "\t\t  } \n" ;

	public String openMethodTemplate(){
		return openMethodTemplate.replace("<initialization>", getUserCode()[1]) ;
	}
	/**
	 * The template for method(#nextTuple)
	 * <nextTuple> is need replaced
	 */
	private String nextTupleMethodTemplate = "\t\t  @Override\n"
			+ "\t\t  public void nextTuple() {\n"
			+ "\t\t    mid = MID.next();\n"
			+ "\t\t    tid = TID.next();"
			+ "\t\t    <nextTuple> \n"
			+ "\t\t } \n" ;

	public String nextTupleMethodTemplate(String spoutName){
		return nextTupleMethodTemplate.replace("<nextTuple>",getUserCode()[2].replace("<logemit>", logemit(spoutName))) ;
	}
	/**
	 * The template for method(#declare)
	 * <declare> is need replaced
	 */
	private String declareOutputFieldsMethodTemplate = "\t\t  @Override\n"
			+ "\t\t  public void declareOutputFields(OutputFieldsDeclarer declarer) {\n"
			+ "\t\t    <declare> \n"
			+ "\t\t } \n";

	public String declareOutputFieldsMethodTemplate(){
		String stream = spout.getSpoutStream();
		String field = spout.getSpoutComponentName() ;
		// declarer.declareStream("S2", new Fields("word","tid", "mid","sid"));
		String declare = "declarer.declareStream("+stream+", new Fields("+field+",\"tid\", \"mid\",\"sid\""+"))" ;
		return declareOutputFieldsMethodTemplate.replace("<declare>",declare) ;
	}
	/**
	 * The template for method(#ack)
	 * <logack> is need replaced
	 */
	private String ackMethodTemplate = "\t\t  @Override\n"
			+ "\t\t  public void ack(Object id) {\n"
			+ "\t\t   <logack> \n"
			+ "\t\t  }\n";

	public String ackMethodTemplate(){
		return ackMethodTemplate.replace("<logack>", logack) ;
	}
	/**
	 * The template for method(#fail)
	 * <logfail> is need replaced
	 */
	private String failMethodTemplate = "\t\t  @Override\n"
			+ "\t\t  public void fail(Object id) {\n"
			+ "\t\t   <logfail>\n"
			+ "\t\t  }\n" ;

	public String failMethodTemplate(){
		return failMethodTemplate.replace("<logfail>", logfail) ;
	}
	/**
	 * The template for log(#SEmit)
	 * <className> is need replaced
	 */
	private String logemit = "logger.error(\"{}\", EventFactory.newSEmit(<className>, tid, mid, this.sid));" ;

	/**
	 * replace <className> in template logemit
	 * @param spoutName
	 * @return
	 */
	public String logemit(String spoutName){
		return logemit.replace("<className>",spoutName) ;
	}

	private String logack = "logger.error(\"{}\", EventFactory.newSACK(id.toString())) ;" ;
	private String logfail = "logger.error(\"{}\", EventFactory.newSFail(id.toString())) ;" ;


	/**
	 *The method (#getUserCode) is used to get user written code then split to array
	 * split code into 6 part
	 */
	public String[] getUserCode(){
		return spout.getSpoutCodeSimple().split("-");
	}



	

}
