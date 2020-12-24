<<<<<<< HEAD
package com.mon4cc.entity;

import lombok.Data;

@Data
public class Grouping {
	
	 private String groupingId ;
	 private String sourceComponent ;
	 private String targetComponent ;
	 private String grouping ;
	 /*This stream is saved for convenience code auto generated, it need save in database, its important.
	  * KafkaSpout, Spout, Bolt also contains stream, it is convenience modeling.
	  * */
	 private String stream ;
	/*The grouping belongs to which topology*/
	private String topologyId ;

}
=======
package com.mon4cc.entity;

import lombok.Data;

@Data
public class Grouping {
	
	 private String groupingId ;
	 private String sourceComponent ;
	 private String targetComponent ;
	 private String grouping ;
	 /*This stream is saved for convenience code auto generated, it need save in database, its important.
	  * KafkaSpout, Spout, Bolt also contains stream, it is convenience modeling.
	  * */
	 private String stream ;
	/*The grouping belongs to which topology*/
	private String topologyId ;

}
>>>>>>> branch 'master' of https://github.com/xuejunY/mon4cc.git
