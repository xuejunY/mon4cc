<<<<<<< HEAD
package com.mon4cc.mapper;

import java.util.List;

import com.mon4cc.entity.KafkaSpout;
import com.mon4cc.entity.Spout;
import org.springframework.stereotype.Service;

/**
 * KafkaSpoutEntity's operation
 * @author xjyou_
 * @Date 2020年12月15日
 */
@Service
public interface KafkaspoutMapper {
	
	boolean addKafkaSpout(KafkaSpout kafkaSpout) ;

	KafkaSpout selectKafkaSpout(String id);

	boolean updateKafkaSpout(KafkaSpout kafkaSpout);

//	boolean deleteKafkaSpout(String spoutComponentName) ;
//	boolean updateKafkaSpout(String id) ;
//	List<KafkaSpout> getAllKafkaSpouts() ;
//	KafkaSpout getKafkaSpout(String id) ;

}
=======
package com.mon4cc.mapper;

import com.mon4cc.entity.KafkaSpout;
import org.springframework.stereotype.Service;

/**
 * KafkaSpoutEntity's operation
 * @author xjyou_
 * @Date 2020年12月15日
 */
@Service
public interface KafkaspoutMapper {
	
	boolean addKafkaSpout(KafkaSpout kafkaSpout) ;

	KafkaSpout selectKafkaSpout(String id);

	boolean updateKafkaSpout(KafkaSpout kafkaSpout);

//	boolean deleteKafkaSpout(String spoutComponentName) ;
//	boolean updateKafkaSpout(String id) ;
//	List<KafkaSpout> getAllKafkaSpouts() ;
//	KafkaSpout getKafkaSpout(String id) ;

}
>>>>>>> branch 'master' of https://github.com/xuejunY/mon4cc.git
