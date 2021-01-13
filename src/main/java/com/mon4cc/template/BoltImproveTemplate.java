package com.mon4cc.template;

import com.mon4cc.entity.Bolt;
import com.mon4cc.entity.Flow;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xjyou_
 * @Date 2021.01.01
 * The class (@BoltImporveTemplate) is used to generate bolt class, which is upgraded version of class (#BoltTemplate)
 */
@Component
@Data
public class BoltImproveTemplate {

    private Bolt bolt ;
    private List<Flow> inFlows ;
    private List<Flow> outFlows ;

    private String boltClassStructure = ""
            + " <packageAndImportDec>"
            + " <classDec>"
            + " <attrsDecs>"
            + " <prepare>"
            + " <execute>"
            + " <declare>"
            + " <getComponentConfiguration>"
            + " <clean>"
            + " <end>" ;

    public String generateClassText(String topologyName){
        return boltClassStructure.replace("<packageAndImportDec>", packageAndImportTemplate(topologyName))
                .replace("<classDec>", classDecTemplate())
                .replace("<attrsDecs>", attrsDecsTemplate())
                .replace("<prepare>", prepare())
                .replace("<execute>", execute())
                .replace("<declare>", declareOutputFields())
                .replace("<getComponentConfiguration>",getComponentConfiguration)
                .replace("<clean>",clean)
                .replace("<end>", "}") ;
    }

    /**
     * packageAndImportTemplate for generate package name and import
     */
    private String packageAndImportTemplate = ""
            + "package com.mon4cc.<projectName>;\n"
            + "import java.util.*;\n"
            + "import java.util.Map;\n" +
            "import java.util.Random;\n" +
            "import java.util.concurrent.atomic.AtomicInteger;\n" +
            "import org.apache.logging.log4j.LogManager;\n" +
            "import org.apache.logging.log4j.Logger;\n" +
            "import org.apache.storm.task.OutputCollector;\n" +
            "import org.apache.storm.task.TopologyContext;\n" +
            "import org.apache.storm.topology.IRichBolt;\n" +
            "import org.apache.storm.topology.OutputFieldsDeclarer;\n" +
            "import org.apache.storm.tuple.Fields;\n" +
            "import org.apache.storm.tuple.Tuple;\n" +
            "import org.apache.storm.tuple.Values;\n" +
            "import log.EventFactory;\n"+
            "import org.apache.storm.topology.IRichBolt;\n"
            + "import org.slf4j.*;\n"
            + "import log.*;\n" ;

    public String packageAndImportTemplate(String topologyName){
        return packageAndImportTemplate.replace("<projectName>", topologyName) ;
    }



    /**
     * global configuration
     */
    private String attrsDecsTemplate = "\t\t <attrsDecs> \n" ;

    public String attrsDecsTemplate(){
        return attrsDecsTemplate.replace("<attrsDecs>",getBoltUserCode()[0]) ;
    }

    /**
     * class template
     */
    private String classDecTemplate = ""
            + "public class <className> implements IRichBolt{\n"
            + "String id ; \n"
            + "String tid = null ;\n"
            + "OutputCollector collector ;\n"
            + "private static final Logger logger = LogManager.getLogger(<className>.class) ;\n";

    public String classDecTemplate(){
        return classDecTemplate.replace("<className>", bolt.getBoltComponentName()) ;
    }

    /**
     * The template for method(#prepare)
     * <conf> is need replaced
     */
    private String prepare = "\t  @Override\n"
            + "\t  public void prepare(Map stormConf, TopologyContext context, OutputCollector collector){\n"
            + "\t\t  collector = collector ;"
            + "\t\t  <conf>\n"
            + "\t  } \n" ;

    public String prepare(){
        String conf = getBoltUserCode()[1] ;
        return prepare.replace("<conf>", conf) ;
    }

    /**
     * The template for method(#execute)
     * <execute> is need replaced
     */
    private String execute = "\t  @Override\n"
            + "\t  public void execute(Tuple input) {\n"
            + "\t\t  <execute>\n"
            + "\t\t  \n"
            + "\t  }\n" ;

    public String execute(){
        return execute.replace("<execute>",getBoltUserCode()[2].replace("<logstart>",logStart())+
                getBoltUserCode()[4].replace("<logemit>",logEmit()).replace("<logack>",logAck())) ;
    }

    /**
     * logStart for generate start log
     */
    private String logStart = "\t\t logger.error(\"{}\", EventFactory.newTake(\"<boltName>\"," +
            "input.getString(1),input.getString(2), input.getString(3))) ;\n" ;

    public String logStart(){
        return logStart.replace("<boltName>",bolt.getBoltComponentName()) ;
    }
    /**
     * logEmit for generate emit log
     */
    private String logEmit1 = "\t\t logger.error(\"{}\", EventFactory.newEmit(\"<boltName>\"," +
            " tid,input.getString(2), \"<boltEmitStream>\")) ;\n" ;
    private String logEmit2 = "\t\t logger.error(\"{}\", EventFactory.newEmit(\"<boltName>\", " +
            "tid,input.getString(2), \"<boltEmitStream1>\")) ;\n"
            +"\t\t logger.error(\"{}\", EventFactory.newEmit(\"<boltName>\", " +
            "tid,input.getString(2), \"<boltEmitStream2>\")) ;\n";

    public String logEmit(){
        if(outFlows.size()==2){//output flow equals 2
            String emitStream1 = outFlows.get(0).getStream() ;
            String emitStream2 = outFlows.get(1).getStream() ;
            return logEmit2.replace("<boltName>", bolt.getBoltComponentName())
                    .replace("<boltEmitStream1>", emitStream1)
                    .replace("<boltEmitStream2>",emitStream2) ;
        }else if(outFlows.size()==1){//output flow equals 1
            String emitStream = outFlows.get(0).getStream() ;
            return logEmit1.replace("<boltName>", bolt.getBoltComponentName())
                    .replace("<boltEmitStream>", emitStream) ;
        }else{
            System.out.println("you shouldn't model output stream more than 2") ;
            return "" ;
        }
    }
    /**
     * logAck for generate ack log
     */
    private String logAck1 = "\t\t logger.error(\"{}\", EventFactory.newAck(\"<boltName>\"," +
            "input.getString(1),input.getString(2), \"<boltAckStream>\")) ;\n" ;
    private String logAck2 = "\t\t logger.error(\"{}\", EventFactory.newAck(\"<boltName>\"," +
            "input.getString(1),input.getString(2), \"<boltAckStream1>\")) ;\n"
            +"\t\t logger.error(\"{}\", EventFactory.newAck(\"<boltName>\"," +
            "input.getString(1),input.getString(2), \"<boltAckStream2>\")) ;\n" ;
    public String logAck(){
        if(inFlows.size()==2){
            String ackStream1 = inFlows.get(0).getStream() ;
            String ackStream2 = inFlows.get(1).getStream() ;
            return logAck2.replace("<boltName>", bolt.getBoltComponentName())
                    .replace("<boltAckStream1>",ackStream1)
                    .replace("<boltAckStream2>",ackStream2) ;
        }else if(inFlows.size()==1){
            String ackStream = inFlows.get(0).getStream() ;
            return  logAck1.replace("<boltAckStream>",ackStream) ;
        }else{
            System.out.println("you shouldn't model input stream more than 2") ;
            return "" ;
        }
    }

    private String declareOutputFields = "\t  @Override\n"
            + "\t  public void declareOutputFields(OutputFieldsDeclarer declarer) {\n"
            + "\t\t  <declare>\n"
            + "\t  }\n" ;
    public String declareOutputFields(){
        return declareOutputFields.replace("<declare>", generateDeclareMethodContent()) ;
    }
    private String getComponentConfiguration ="\t  @Override\n"
            + "\t  public Map<String, Object> getComponentConfiguration() {\n"
            + "\t\t return null; \n"
            + "\t  }\n" ;
    private String clean ="\t  @Override\n"
            + "\t  public void cleanup() {\n"
            + "\t\t  \n"
            + "\t  }\n" ;

    public String generateDeclareMethodContent(){
        if(outFlows.size()==2){//output flow equals 2, so declare is two. Note, there declare field are same field that is boltName
            String declareStream1 = outFlows.get(0).getStream() ;
            String declareStream2 = outFlows.get(1).getStream() ;
            return  "declarer.declareStream("+"\""+declareStream1+"\""+", new Fields("+"\""+bolt.getBoltComponentName()+"\""+
                    ",\"tid\", \"mid\",\"sid\""+"));\n"
                    +"declarer.declareStream("+"\""+declareStream2+"\""+", new Fields("+"\""+bolt.getBoltComponentName()+"\""+
                    ",\"tid\", \"mid\",\"sid\""+"));\n";
        }else if(outFlows.size()==1){//output flow equals 1
            String declareStream = outFlows.get(0).getStream() ;
            return "declarer.declareStream("+"\""+declareStream+"\""+", new Fields("+"\""+bolt.getBoltComponentName()+"\""+
                    ",\"tid\", \"mid\",\"sid\""+"));\n" ;
        }else{
            System.out.println("you shouldn't model output stream more than 2") ;
            return "" ;
        }

    }

    /**
     * get user code from database
     *
     * bolt user code example as following:
     * ----------------------------------------------
     * 	OutputCollector _collector ;
     * 	Random _rand ;
     * 	-
     * 		_rand = new Random() ;
     * 	-
     * 		<logstart>
     * 		 String sentence = input.getString(0);
     * 		 Values values = null ;
     *          for(String word: sentence.split(" ")) {
     *                 tid = TID.next() ;
     *             	values = new Values(word,tid,input.getValue(2),"S2") ;
     *             	-
     *             	_collector.emit("S2",input, values) ;
     *     			-
     *             	<logemit>
     *          }
     *          _collector.ack(input) ;
     *          <logack>
     *
     * @return String[]
     */
    public  String[] getBoltUserCode(){
        return bolt.getBoltCodeSimple().split("-") ;
    }





}
