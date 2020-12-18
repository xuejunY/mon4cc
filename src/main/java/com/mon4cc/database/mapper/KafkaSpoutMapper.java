package com.mon4cc.database.mapper;

import java.util.List;

import com.mon4cc.database.entity.KafkaSpout;

/**
 * KafkaSpoutEntity's operation
 * @author xjyou_
 * @Date 2020年12月15日
 */
public interface KafkaSpoutMapper {
	
	boolean addKafaSpout(KafkaSpout kafkaSpout) ;
	boolean deleteKafkaSpout(String spoutComponentName) ;
	boolean updateKafkaSpout(String id) ;
	List<KafkaSpout> getAllKafkaSpouts() ;
	KafkaSpout getKafkaSpout(String id) ;

}
