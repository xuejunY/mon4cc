package com.mon4cc.entity;

import lombok.Data;
/**
 * Map TopologyConfiguration class to database TopologyConfiguration table.
 * @author xjyou_
 * @Date 2020年12月15日
 */
@Data
public class TopologyConfiguration {
	
	/*Topology id*/
	private String topologyId ;
	
	/*Topology name*/
	private String topologyName ;
	
	/*Whether topology is deployed in local or distributed, 
	 * true is local and false is distributed */
	private boolean isLocal ;
	
	/*Topology's xml file*/
	private String topologyXml ;
	
	/*Topology's description*/
	private String description ;
	
	/*Topology's state*/
	private String state ;
	
	/*Topology's creater*/
	private String creater ;
	
	/*Topology's create time*/
	private String createTime ;

	/*Topology's complete code */
	private String completeConfigurationCode ;
}

