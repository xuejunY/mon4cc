package com.mon4cc.autogeneratecode.entity;

import lombok.Data;
/**
 * The class view parse results as object and send
 * @author xjyou_
 * @Date 2020年12月16日
 */
@Data
public class Spout {
	private String spoutComponentName ;
	private int spoutParallelism ;
	private String completeSpoutCode;
}
