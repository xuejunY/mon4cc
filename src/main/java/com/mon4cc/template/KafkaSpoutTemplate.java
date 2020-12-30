package com.mon4cc.template;

import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

import com.mon4cc.entity.KafkaSpout;
import lombok.Data;
import org.junit.Test;
import org.springframework.stereotype.Component;

@Component
@Data
public class KafkaSpoutTemplate {
	private KafkaSpout kafkaSpout ;
	private String kafkaSpoutClassStructure = ""
			+ " <packageAndImportDec>"
			+ " <classDec>"
			+ " <attrsDecs>"
			+ " <open>"
			+ " <nextTuple>"
			+ " <declareOutputFields>"
			+ " <ack>"
			+ " <fail>"
			+ " <kafkaInit>"
			+ " <end>" ;

	public String generateClassText (String topologyName, String kafkaSpoutName){
	    return kafkaSpoutClassStructure.replace("<packageAndImportDec>",packageAndImportTemplate(topologyName))
                .replace("<classDec>", classDecTemplate(kafkaSpoutName))
                .replace("<attrsDecs>", attrsDecsTemplate())
                .replace("<open>", openMethodTemplate())
                .replace("<nextTuple>", nextTupleMethodTemplate(kafkaSpoutName))
                .replace("<declareOutputFields>", declareOutputFieldsMethodTemplate())
                .replace("<ack>", ackMethodTemplate())
                .replace("<fail>", failMethodTemplate())
                .replace("<kafkaInit>",kafakInitTemplate())
                .replace("<end>","}") ;
    }

	/**
	 * The template for package and import.
	 * <projectName> is need be replaced
	 */
	private String packageAndImportTemplate = "com.mon4cc.<projectName>\n"
			+ "import java.time.Duration;\n"
			+ "import java.util.*;\n"
			+ "import org.apache.kafka.*;\n"
			+ "import org.apache.storm.*;\n"
			+ "import org.slf4j.*;\n"
			+ "import log.*\n"
			+ "\n" ;

	public String packageAndImportTemplate(String topologyName){
		return packageAndImportTemplate.replace("<projectName>", topologyName) ;
	}

	/**
	 * The template for  class.
	 * <className> is need be replaced
	 */
    private String classDecTemplate = "public class <className> extends BaseRichSpout { \n"
            + "\t private static final Logger logger = LogManager.getLogger(<className>.class) ; \n"
            + "\t private static final IDGenerator MID = IDFactory.getIDGenerator(\"mid_\") ;\n"
            + "\t public static final IDGenerator TID = IDFactory.getIDGenerator(\"tid_\") ;\n"
            + "\t String sid ;\n"
            + "\t String mid = null ;\n"
            + "\t String tid = null ;\n" ;

	public String classDecTemplate(String kafkaSpoutName){
		return classDecTemplate.replace("<className>",kafkaSpoutName) ;
	}

	/**
	 * The template for global attributes.
	 * <attrsDecs> is need be replaced
	 */
	public String attrsDecsTemplate(){
		return getUserCode()[0] ;
	}

    /**
     * The template for method(#open)
     * <initialization> is need be replaced
     */
    private String openMethodTemplate = "\t\t  @Override\n"
            + "\t public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {\n"
            + "\t  <initialization>\n"
            + "\t  } \n" ;

    public String openMethodTemplate(){
        return openMethodTemplate.replace("<initialization>", getUserCode()[1]) ;
    }

    /**
     * The template for method(#nextTuple)
     * <nextTuple> is need be replaced
     */
    private String nextTupleMethodTemplate = "\t @Override\n"
            + "\t public void nextTuple() {\n"
            + "\t    mid = MID.next();\n"
            + "\t    tid = TID.next();"
            + "\t    <nextTuple> \n"
            + "\t } \n" ;

    public String nextTupleMethodTemplate(String kafkaSpoutName){
        return nextTupleMethodTemplate.replace("<nextTuple>",getUserCode()[2].replace("<logemit>", logemit(kafkaSpoutName))) ;
    }
    /**
     * The template for method(#declare)
     * <declare> is need be replaced
     */
    private String declareOutputFieldsMethodTemplate = "\t @Override\n"
            + "\t public void declareOutputFields(OutputFieldsDeclarer declarer) {\n"
            + "\t  <declare> \n"
            + "\t } \n";

    public String declareOutputFieldsMethodTemplate(){
        String stream = kafkaSpout.getKafkaSpoutStream();
        String field = kafkaSpout.getSpoutComponentName() ;
        String declare = "declarer.declareStream("+stream+", new Fields("+field+",\"tid\", \"mid\",\"sid\""+"))" ;
        return declareOutputFieldsMethodTemplate.replace("<declare>",declare) ;
    }
    /**
     * The template for method(#ack)
     * <logack> is need be replaced
     */
    private String ackMethodTemplate = "\t @Override\n"
            + "\t public void ack(Object id) {\n"
            + "\t  <logack> \n"
            + "\t }\n";

    public String ackMethodTemplate(){
        return ackMethodTemplate.replace("<logack>", logack) ;
    }
    /**
     * The template for method(#fail)
     * <logfail> is need be replaced
     */
    private String failMethodTemplate = "\t @Override\n"
            + "\t public void fail(Object id) {\n"
            + "\t  <logfail>\n"
            + "\t }\n" ;

    public String failMethodTemplate(){
        return failMethodTemplate.replace("<logfail>", logfail) ;
    }

    private String logack = "logger.error(\"{}\", EventFactory.newSACK(id.toString())) ;" ;
    private String logfail = "logger.error(\"{}\", EventFactory.newSFail(id.toString())) ;" ;

    /**
     * The template for kafka configuration
     * <bootstrap.servers>, <max.poll.records>, etc, are need be replaced
     */
    private String kafakInitTemplate = "\t public void kafkaInit(){ \n"
            + "\t Properties props = new Properties();\n"
            + "\t props.put(\"bootstrap.servers\", <bootstrap.servers>);  \n"
            + "\t props.put(\"max.poll.records\", <max.poll.records>);\n"
            + "\t props.put(\"enable.auto.commit\",<enable.auto.commit>);\n"
            + "\t props.put(\"group.id\", <group.id>);\n"
            + "\t props.put(\"auto.offset.reset\", <auto.offset.reset>);\n"
            + "\t props.put(\"key.deserializer\", \"org.apache.kafka.common.serialization.StringDeserializer\");\n"
            + "\t props.put(\"value.deserializer\", \"org.apache.kafka.common.serialization.StringDeserializer\");" ;

    public String kafakInitTemplate(){
        return kafakInitTemplate.replace("<bootstrap.servers>", kafkaSpout.getBoostrapServer())
                .replace("<max.poll.records>", String.valueOf(kafkaSpout.getMaxPollRecord()))
                .replace("<enable.auto.commit>", String.valueOf(kafkaSpout.isAutoCommit()))
                .replace("<group.id>", kafkaSpout.getGroupId())
                .replace("<auto.offset.reset>", kafkaSpout.getOffsetReset()) ;
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


	/**
	 *The method (#getUserCode) is used to get user written code then split to array
	 * split code into 6 part
	 */
	public String[] getUserCode(){
		return kafkaSpout.getKafkaSpoutCodeSimple().split("-");
	}

	/**
	 * The method (#transNumberToBoolean) is used to transfer number 0 to false and 1 to true
	 * @return
	 */
	/*
	public boolean transNumberToBoolean(){
		if(kafkaSpout.getAutoCommit()==1) return true ;
		else return false ;
	}
	*/

}
