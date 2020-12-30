package com.mon4cc.template;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mon4cc.entity.*;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.mon4cc.parse.ModelParse;
import org.springframework.stereotype.Component;

/**
 * @author xjyou_
 * @Date 2020.12.30
 */
@Component
@Data
public class ComfigurationTemplate {

	private List<Spout> spouts ;
	private List<KafkaSpout> kafkaSpouts ;
	private List<Bolt> bolts ;
	private List<Flow> flows ;


	private String comfigurationClassStructure = ""
			+ " <packageAndImportDec>"
			+ " <classDec>"
			+ " <main>"
			+ " <end>" ;

	public String generateClassText(String topologyName, boolean isLocal){


		return comfigurationClassStructure.replace("<packageAndImportDec>", packageAndImportTemplate(topologyName))
				.replace("<classDec>", classDecTemplate(topologyName))
				.replace("<main>", mainMethodTemplate(isLocal, topologyName))
				.replace("<end>", "}");
	}

	/**
	 * The template for package and import.
	 * <projectName> is need be replaced
	 */
	private String packageAndImportTemplate = "com.mon4cc.<projectName>\n"
			+ "import org.apache.storm.*;\n"
			+ "\n" ;

	public String packageAndImportTemplate(String topologyName){
		return packageAndImportTemplate.replace("<projectName>", topologyName) ;
	}

	/**
	 * The template for  class.
	 * <className> is need be replaced
	 */
	private String classDecTemplate = "public class <className> { \n";

	public String classDecTemplate(String topologyName){
		return classDecTemplate.replace("<className>",topologyName) ;
	}

	private String mainMethodLocalTemplate = "\t public static void main(String[] args) throws Exception {\n"
			+"\t  TopologyBuilder builder = new TopologyBuilder();\n"
			+"\t  <setSpout>\n"
			+"\t  <setBolt>\n"
			+"\t  LocalCluster cluster = new LocalCluster();\n"
			+"\t  cluster.submitTopology(\"<topologyName>\", config, builder.createTopology());"
			+"\t }\n";

	private String mainMethodDistributedTemplate = "\t public static void main(String[] args) throws Exception {\n"
			+"\t  TopologyBuilder builder = new TopologyBuilder();\n"
			+"\t  <setSpout>\n"
			+"\t  <setBolt>\n"
			+"\t  StormSubmitter.submitTopology(\"<topologyName>\", config, builder.createTopology());"
			+"\t }\n";

	public String mainMethodTemplate(boolean isLocal, String topologyName){
		if(isLocal){
			return mainMethodLocalTemplate.replace("<setSpout>",spoutConfiguration())
					.replace("<setBolt>",boltConfiguration())
					.replace("<topologyName>", topologyName) ;
		} else{
			return mainMethodDistributedTemplate.replace("<setSpout>",spoutConfiguration())
					.replace("<setBolt>",boltConfiguration())
					.replace("<topologyName>", topologyName) ;
		}

	}

	/**
	 * The method (#spoutConfiguration) is for generate spout configuration
	 * e.g.  builder.setSpout("spout", new RandomSentenceSpout(), 1);
	 * @return
	 */
	private String spoutConfiguration = "builder.setSpout(\"<spoutName>\", new <spoutName>(), <parallelism>" ;
	private String kafkaSpoutConfiguration = "builder.setSpout(\"<kafkaSpoutName>\", new <kafkaSpoutName>(), <parallelism>";

	public String spoutConfiguration(){

		StringBuilder sb = new StringBuilder() ;
		for(Spout spout : spouts) {
			String content = spoutConfiguration.replace("<spoutName>", spout.getSpoutComponentName())
					.replace(" <parallelism>", String.valueOf(spout.getSpoutParallelism())) ;
			sb.append(content) ;
		}

		for(KafkaSpout kafkaSpout : kafkaSpouts) {
			String content = kafkaSpoutConfiguration.replace("<kafkaSpoutName>", kafkaSpout.getSpoutComponentName())
					.replace("<parallelism>", String.valueOf(kafkaSpout.getSpoutParallelism())) ;
			sb.append(content) ;
		}
		return sb.toString() ;
	}
	// builder.setBolt("split", splitBolt, 2).shuffleGrouping("spout","S1");
	//there is one flow into bolt
	private String boltConfiguration1 = "builder.setBolt(\"<boltName>\", new <boltName>(), <parallelism>).<grouping>(<sourceComponent>,<stream>); " ;
	//there are two flow into bolt
	private String boltConfiguration2 = "builder.setBolt(\"<boltName>\", new <boltName>(), <parallelism>).<grouping1>(<sourceComponent1>,<stream1>)"
			+ ".<grouping2>(<sourceComponent2>,<stream2>); " ;
	//there are three flow into bolt
	private String boltConfiguration3 = "builder.setBolt(\"<boltName>\", new <boltName>(), <parallelism>).<grouping1>(<sourceComponent1>,<stream1>)"
			+ ".<grouping2>(<sourceComponent2>,<stream2>).<grouping3>(<sourceComponent3>,<stream3>); " ;
	//ther are flow for fieldsGrouping
	private String boltConfigurationField = "builder.setBolt(\"<boltName>\", new <boltName>(), <parallelism>).<grouping>(<sourceComponent>,<stream>, new Fields<field>);" ;

	public String boltConfiguration(){
		StringBuilder sb = new StringBuilder() ;
		for(Bolt bolt : bolts){
			List <Flow> tempFlow = null ;
			/*
			The way that finding all flow for every bolt is too waste time, it may be improved in the future.
			The way that Changing database structure may be improved quickly.
			 */
			for(Flow flow : flows){
				if(flow.getTargetComponent().equals(bolt.getId())) tempFlow.add(flow) ;
			}

			if(tempFlow.size()==2){
				//there is two flow into bolt
				String [] result = new String[tempFlow.size()*3] ;
				int i = 0 ;
				for(Flow flow :tempFlow) {
					result[i] = flow.getGroupingType() ;
					result[i+1] = flow.getSourceComponent() ;
					result[i+2]= flow.getStream() ;
					i+=3 ;
				}
				/*
				type of two flow into bolt is considered, because the way of replace cannot be loop.
				if there is good idea , it will be improve.
				stereotype is temporary.
				 */
				String content = boltConfiguration2.replace("<boltName>", bolt.getBoltComponentName())
						.replace("<parallelism>", String.valueOf(bolt.getBoltParallelism()))
						.replace("<grouping1>", result[0])
						.replace("<sourceComponent1>", result[1])
						.replace("<stream1>", result[2])
						.replace("<grouping2>", result[3])
						.replace("<sourceComponent2>", result[4])
						.replace("<stream2>", result[5]);
				sb.append(content);
			}else if(tempFlow.size()==3){
				String [] result = new String[tempFlow.size()*3] ;
				int i = 0 ;
				for(Flow flow :tempFlow) {
					result[i] = flow.getGroupingType() ;
					result[i+1] = flow.getSourceComponent() ;
					result[i+2]= flow.getStream() ;
					i+=3 ;
				}
				/*
				type of three flow into bolt is considered, because the way of replace cannot be loop.
				if there is good idea , it will be improve.
				stereotype is temporary.
				 */
				String content = boltConfiguration3.replace("<boltName>", bolt.getBoltComponentName())
						.replace("<parallelism>", String.valueOf(bolt.getBoltParallelism()))
						.replace("<grouping1>", result[0])
						.replace("<sourceComponent1>", result[1])
						.replace("<stream1>", result[2])
						.replace("<grouping2>", result[3])
						.replace("<sourceComponent2>", result[4])
						.replace("<stream2>", result[5])
						.replace("<grouping3>", result[6])
						.replace("<sourceComponent3>", result[7])
						.replace("<stream3>", result[8])
						;
				sb.append(content);

			} else if(tempFlow.size()==1) {
				//there is one flow into bolt
				for(Flow flow : tempFlow) {
					/*
					 if grouping is equals to fields grouping , the grouping is need consider solely, cause it need
					 field additionally
					 */

					if(flow.getGroupingType().equals("fieldsGrouping")){
						String content = boltConfigurationField.replace("<boltName>", bolt.getBoltComponentName())
								.replace("<parallelism>", String.valueOf(bolt.getBoltParallelism()))
								.replace("<grouping>", flow.getGroupingType())
								.replace("<sourceComponent>", flow.getSourceComponent())
								.replace("<stream>", flow.getStream())
								.replace("<field>", bolt.getBoltComponentName());
						sb.append(content);
					}else {
						String content = boltConfiguration1.replace("<boltName>", bolt.getBoltComponentName())
								.replace("<parallelism>", String.valueOf(bolt.getBoltParallelism()))
								.replace("<grouping>", flow.getGroupingType())
								.replace("<sourceComponent>", flow.getSourceComponent())
								.replace("<stream>", flow.getStream());
						sb.append(content);
					}
				}
			} else {
				//throw exception
			}
		}
		return sb.toString() ;
	}
}
