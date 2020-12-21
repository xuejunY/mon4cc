package com.mon4cc.parse.entity;

import lombok.Data;


/**
 * A entity to receive data from web
 * @author xjyou_
 * @Date 2020年12月12日
 */
@Data
public class ModelParseDTO {
	/*
	 * All fields are string type ;
	 */
	private String tid ;
	private String topologyName ;
	private String isLocal ;
	private String modelXml ;


}
