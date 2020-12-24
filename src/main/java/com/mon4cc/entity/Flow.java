package com.mon4cc.entity;

import lombok.Data;

@Data
public class Flow {
	
	 private String groupingId ;
	 private String sourceComponent ;
	 private String targetComponent ;
	 private String groupingType ;
	 /*This stream is saved for convenience code auto generated, it need save in database, its important.
	  * KafkaSpout, Spout, Bolt also contains stream, it is convenience modeling.
	  * */
	 private String stream ;
	/*The grouping belongs to which topology*/
	private String topologyId ;

}
