<<<<<<< HEAD
package com.mon4cc.entity;


import lombok.Data;
/**
 * Map SpoutEntity class to Spout table.
 * @author xjyou_
 * @Date 2020年12月15日
 */
@Data
public class Spout {
	
	/*Spout id*/
	private String id ;
	
	/*Spout name*/
	private String spoutComponentName ;
	
	/*spout parallelism*/
	private Integer spoutParallelism ;
	
	/*stream start with  spout. e.g. S1_S2*/
	private String spoutStream ;

	/*The spout belongs to which topology*/
	private String topologyId ;
	
	/*The spout code from web for cover code to web*/
	private String spoutCode ;
	
	/*The spout code from web for auto code generated to web*/
	private String spoutCodeSimple ;

	private String completeSpoutCode;
}
=======
package com.mon4cc.entity;


import lombok.Data;
/**
 * Map SpoutEntity class to Spout table.
 * @author xjyou_
 * @Date 2020年12月15日
 */
@Data
public class Spout {
	
	/*Spout id*/
	private String id ;
	
	/*Spout name*/
	private String spoutComponentName ;
	
	/*spout parallelism*/
	private Integer spoutParallelism ;
	
	/*stream start with  spout. e.g. S1_S2*/
	private String spoutStream ;

	/*The spout belongs to which topology*/
	private String topologyId ;
	
	/*The spout code from web for cover code to web*/
	private String spoutCode ;
	
	/*The spout code from web for auto code generated to web*/
	private String spoutCodeSimple ;

	private String completeSpoutCode;
}
>>>>>>> branch 'master' of https://github.com/xuejunY/mon4cc.git
