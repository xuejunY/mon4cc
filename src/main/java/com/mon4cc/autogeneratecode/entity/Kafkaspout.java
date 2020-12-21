package com.mon4cc.autogeneratecode.entity;

import lombok.Data;
/**
 * The class view parse results as object and send
 * @author xjyou_
 * @Date 2020年12月16日
 */
@Data
public class Kafkaspout {
	
	private String spoutComponentName ;
	private int spoutParallelism ;
	private String kafkaBootstrapAddress ;
	private int maxPollRecords ;
	private boolean enableAutoCommit ;
	private String groupId ;
	private String autoOffsetReset ;
	private String completeKafkaSpoutCode;
	
}
