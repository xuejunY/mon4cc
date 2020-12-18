/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mon4cc.parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import org.camunda.bpm.model.bpmn.instance.IntermediateThrowEvent;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;

import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.junit.Before;
import org.junit.Test;

import com.mon4cc.parse.entity.ModelParseDTO;





public class ModelParse {

  protected BpmnModelInstance modelInstance;
  private int tid ;
  private String topologyName ;
  private boolean isLocal ;
  private String modelXml ;
  Collection<IntermediateThrowEvent> kafkaSpouts = null ;
  private static final Logger logger = LogManager.getLogger(ModelParse.class) ;
  
  public String receiveAndParseData(ModelParseDTO modelParseDTO) {
	  
	  tid = Integer.parseInt(modelParseDTO.getTid()) ;
	  topologyName = modelParseDTO.getTopologyName() ;
	  isLocal = Boolean.parseBoolean(modelParseDTO.getIsLocal()) ;
	  
	  modelXml = modelParseDTO.getModelXml() ;
	  writeStringToFile(modelXml) ;
	  loadProcess() ;
	  return null ;
  }
  /*
   * Get bolt configuration based on BPMN task,
   * and insert into database ;
   */
  public boolean parseBolt() {
	  boolean flag = false ;
	  Collection<Task> bolts = modelInstance.getModelElementsByType(Task.class) ;
	  for(Task bolt: bolts) {
		 String medDatas = bolt.getId() ;
		 String[]data = medDatas.split("_") ;
		 
		 /*boltId, boltParallelism boltComponentName are need save to databased*/
		 String boltId = data[0]+"_"+data[1] ;
		 Integer boltParallelism = Integer.parseInt(data[2]) ;
		 
		 String boltStream = data[3] ;
		 
		 String boltComponentName = bolt.getName() ;
		 
		 logger.info("Component Name: {}, Bolt Parallelism: {}",boltComponentName,boltParallelism) ;
	  }
	  flag = true ;
	  return flag ;
  }
  /*
   * Get grouping and stream configuration based on BPMN sequenceFlow,
   * and insert into database ;
   */
  public boolean parseGrouping() {
	  boolean flag = false ;
	  Collection<SequenceFlow> flows = modelInstance.getModelElementsByType(SequenceFlow.class) ;
	  for(SequenceFlow flow : flows) {
		  String [] datas = flow.getName().split("_") ;
		  
		  /* groupingId, sourceComponent,targetComponent,grouping,stream are need save database*/
		  String groupingId = flow.getId() ;
		  //grouping beginning which spout or bolt.
		  String sourceComponent = flow.getSource().getName() ;
		  //grouping ending which bolt
		  String targetComponent = flow.getTarget().getName() ;
		  //grouping name, e.g. shuffle grouping
		  String grouping = datas[0] ;
		  
		  //stream type in the grouping, e.g. S1, S2 are stream.
		  String stream = datas[1] ;
		  logger.info("Grouping: {}, Stream: {},Source: {},Target: {}",grouping,stream,sourceComponent,targetComponent) ;
	  }
	  flag = true ;
	  
	  return flag ;
  }
  /*
   * Get spout configuration based on BPMN startEvent,
   * and insert into database
   */
  public boolean parseSpout() {
	  boolean flag = false ;
	  Collection<StartEvent>spouts = modelInstance.getModelElementsByType(StartEvent.class) ;
		 for(StartEvent spout : spouts) {
			
			 String medData	= spout.getId() ;
			 String [] datas = medData.split("_") ;
			 
			 /*spoutId, spoutComponentName,spoutParallelism are need save in database*/
			 String spoutId = datas[0]+"_"+datas[1] ;
			 // get spout parallelism
			 Integer spoutParallelism =Integer.parseInt(datas[2]) ;
			 
			 String spoutStream = datas[3] ;
			 
			 // get spout component name
			 String spoutComponentName = spout.getName() ;
			 
			 logger.info("Parallelism: {}, ComponentName: {}",spoutParallelism,spoutComponentName) ;
			 /*
			 Collection<SequenceFlow> outGroupings = spout.getOutgoing() ;
			 for(SequenceFlow outGrouping: outGroupings) {
				String[]groupingAndStream = outGrouping.getName().split("_") ;
				String group = groupingAndStream[0] ;
				String stream = groupingAndStream[1] ;
			 }
			 */
		 }
	  flag = true ;
	  
	  return flag ;
			  
  }
  
  /*
   *  Get kafka Spout Configuration based on BPMN intermediateThrowEvent,
   *  and insert into database
   */
  
 public boolean parseKafkaSpout() {
	 boolean flag = false ;
	 kafkaSpouts = modelInstance.getModelElementsByType(IntermediateThrowEvent.class) ;
	 for(IntermediateThrowEvent spout : kafkaSpouts) {
		 String kafkaSpout = spout.getId() ;
		 String[] datas = kafkaSpout.split("_") ;
		 /*kafkaSpoutId, kafkaSpoutComponentName, parallelism
		  *kafkaBootstrapAddress, maxPollRecords,enableAutoCommit
		  *,groupId,autoOffsetReset are need saved in database*/
		 String kafkaSpoutId = datas[0]+"_"+datas[1] ;
		 // get kafka spout name
		 String kafkaSpoutComponentName = spout.getName() ;
		 
		 // get kafka spout parallelism
		 int parallelism = Integer.parseInt(datas[2]) ;
		 //get S1-S2-S3
		 String kafkaSpoutStream = datas[3] ;
		 //kafka spout configuration
		 //bootstrap.servers
		 String kafkaBootstrapAddress = datas[4].replace('-', ':') ;
		 //max.poll.records
		 int maxPollRecords = Integer.parseInt(datas[5]) ;
		 //enable.auto.commit
		 boolean enableAutoCommit = Boolean.parseBoolean(datas[6]) ;
		 //group.id
		 String groupId = datas[7] ;
		 //auto.offset.reset
		 String autoOffsetReset = datas[8] ;
		 
		 String topic = datas[9] ;
//		 System.out.println("Component Name :"+componentName+", parallelism: "+parallelism+", bootstrap.servers: "+
//		 kafkaBootstrapAddress+"\n"+"max.poll.records: "+maxPollRecords+", enable.auto.commit: "+enableAutoCommit+
//		 ", group.id: "+groupId+", auto.offset.reset: "+autoOffsetReset) ;
		 logger.info("Component Name :{}, parallelism: {}, bootstrap.servers: {},max.poll.records: {}, enable.auto.commit: {}, "
		 		+ "group.id: {}, auto.offset.reset: {}, topic: {}",kafkaSpoutComponentName,parallelism,kafkaBootstrapAddress
		 		,maxPollRecords,enableAutoCommit,groupId,autoOffsetReset,topic) ;
		 //kafka spout outgoing grouping
		 /*
		 Collection<SequenceFlow> outGroupings = spout.getOutgoing() ;
		 for(SequenceFlow outGrouping: outGroupings) {
			String[]groupingAndStream = outGrouping.getName().split("_") ;
			String group = groupingAndStream[0] ;
			String stream = groupingAndStream[1] ;
		 }
		 */
	 }
	 flag = true ;
	 
	 return flag ;

 }
 /*
 public void parseKafkaSpout(KafkaSpout kfks) {
	 
	 kafkaSpouts = modelInstance.getModelElementsByType(IntermediateThrowEvent.class) ;
	 for(IntermediateThrowEvent spout : kafkaSpouts) {
		 String kafkaSpout = spout.getId() ;
		 kfks.componentName.add(spout.getName()) ;
		 String[] datas = kafkaSpout.split("_") ;
		 
		 kfks.parallelism.add(Integer.parseInt(datas[2])) ;
		 //kafka spout configuration
		 //bootstrap.servers
		 kfks.kafkaBootstrapAddress.add(datas[3].replace('-', ':')) ;
		 //max.poll.records
		 kfks.maxPollRecords.add(Integer.parseInt(datas[4])) ;
		 //enable.auto.commit
		 kfks.enableAutoCommit.add(Boolean.parseBoolean(datas[5])) ;
		 //group.id
		 kfks.groupId.add(datas[6]) ;
		 //auto.offset.reset
		 kfks.autoOffsetReset.add(datas[7]) ;

		 //kafka spout outgoing grouping
		 Collection<SequenceFlow> outGroupings = spout.getOutgoing() ;
		 for(SequenceFlow outGrouping: outGroupings) {
			 
		 }
	 }
	 	logger.info("Parse Kafka Spout successeflly");

 }
 */
  /**
   * Write model of string type into file 
   * @param modelXml
   */
  public void writeStringToFile(String modelXml) {
	  //put string type of bpmn.model into file .
	  //File bpmnModel=new File("D:/java/Spring_mvcWorkSpace/mon4cc/src/main/resources/"+topologyName+".bpmn");
	  FileOutputStream fos = null ;
	  File bpmnModel=new File(getResourcePath()+"/"+topologyName+".bpmn") ;
      if(!bpmnModel.exists()){  
          try {
        	  bpmnModel.createNewFile() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}  
     }
      byte bytes[]=new byte[512] ;   
      bytes=modelXml.getBytes() ;  
      int b=bytes.length ;   
      
	try {
		fos = new FileOutputStream(bpmnModel) ;
		fos.write(bytes,0,b) ;
		
		logger.info("Model writes to file successfully");
	    
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		logger.info("FileNotFoundException: "+e) ;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		logger.info("IO Exception: "+e) ;
	} finally {
		if(fos != null)
			try {
				fos.close() ;
				logger.info("FileOutputStream is closed ") ;
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace() ;
				logger.info("FileOutputStream is closed failure: "+e) ;
			}
	}

  }
  
  /*
   * Get resource path
   */
  public String getResourcePath() {
	  String path = ModelParse.class.getClassLoader().getResource("").getPath() ;
	  logger.info("Get Resource Path: "+path) ;
	  return path ;
  }

  @Before
  public void loadProcess() {
    // read a BPMN model from an input stream
	 /*
    modelInstance = Bpmn.readModelFromStream(getClass().getClassLoader().getResourceAsStream(topologyName+".bpmn")) ;
    logger.info("Read a BPMN model from an input stream ") ;
    */
	  modelInstance = Bpmn.readModelFromStream(getClass().getClassLoader().getResourceAsStream("demo.bpmn"));
  }


  /*
   * You can access every element of the BPMN model by id.
   */
  
  public void findElementById() {
	StartEvent startEvent = modelInstance.getModelElementById("SequenceFlow_0h21x7r") ;
	System.out.println(startEvent.getName()) ;
	
	/*
    // get the start event of the process by id
    StartEvent startEvent = modelInstance.getModelElementById("startEvent");
    
    
    assertThat(startEvent.getName()).isEqualTo("Start Process");

    // get the forking gateway by id
    ExclusiveGateway fork = modelInstance.getModelElementById("gatewayFork");
    assertThat(fork.getName()).isEqualTo("Fork");

    // get the end event of the process by id
    EndEvent endEvent = modelInstance.getModelElementById("endEvent");
    assertThat(endEvent.getName()).isEqualTo("End Process");
    */
  }


  /*
   *  You can access elements by their type.
   */
  @Test
  public void findElementByType() {
	  Collection<SequenceFlow> flowNode = modelInstance.getModelElementsByType(SequenceFlow.class);
	
	    // there exist 8 flow nodes: start event, service task, 2x exclusive gateways, 2x user tasks, script task, end event
	 for(SequenceFlow f : flowNode) {
//		 System.out.println(f.getAttributeValue("name")) ; 
		 String[]groupingAndStream = f.getName().split("_") ;
		 System.out.println("Target: "+f.getTarget().getName()+" Source: "+f.getSource().getName()) ;
		 System.out.println("：(") ;
		 String grouping = groupingAndStream[0] ;
		 String stream = groupingAndStream[1] ;
		 System.out.println("Grouping："+grouping+" ===***=== Stream: "+stream) ;
		 //System.out.println(f.getId()) ;
		 
	 }
	   
	/*
    // get all service tasks from the model
    Collection<ServiceTask> serviceTasks = modelInstance.getModelElementsByType(ServiceTask.class);
    // it only exists one service task
    assertThat(serviceTasks).hasSize(1);

    // get all events (eg start, intermediate, boundary and end events) from the model
    Collection<Event> events = modelInstance.getModelElementsByType(Event.class);
    // it exists a start and an end event
    assertThat(events).hasSize(2);

    // get all flow nodes in the model
    Collection<FlowNode> flowNodes = modelInstance.getModelElementsByType(FlowNode.class);
    // there exist 8 flow nodes: start event, service task, 2x exclusive gateways, 2x user tasks, script task, end event
    assertThat(flowNodes).hasSize(8);

    // get all extension elements in the model
    Collection<ExtensionElements> extensionElements = modelInstance.getModelElementsByType(ExtensionElements.class);
    // the start event and the end event contain extension elements
    assertThat(extensionElements).hasSize(2);
    */
  }

}
