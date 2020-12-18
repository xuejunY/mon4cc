package com.mon4cc.database.entity;

import lombok.Data;
/**
 * Map the BoltEntity class to database Bolt table.
 * @author xjyou_
 * @Date 2020年12月15日
 */
@Data
public class Bolt {
	
	/*Bolt id*/
	private String id ;
	
	/*Bolt name*/
	private String boltComponentName ;
	
	/*Bolt Parallelism*/
	private Integer boltParallelism ;
	
	/*Stream start with bolt. e.g. S1_S2*/
	private String boltStream ;
	
	/*Bolt code from web for auto code generated */
	private String boltCodeSimple ;
	
	/**Bolt code from web for recover code to web*/
	private String boltCode ;
	
	/*The bolt belongs to which topology*/
	private String topologyid ;
}
