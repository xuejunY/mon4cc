package com.mon4cc.database.service.impl;

import com.mon4cc.database.entity.KafkaSpout;
import com.mon4cc.database.mapper.KafkaspoutMapper;
import com.mon4cc.database.service.IKafkaspoutService;
import org.apache.logging.log4j.core.appender.mom.kafka.KafkaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangsanhe
 * @since 2020-12-19
 */
@Service
public class KafkaspoutServiceImpl implements IKafkaspoutService {

    @Autowired
    private KafkaspoutMapper kafkaspoutMapper;

    @Override
    public boolean insert_batch(KafkaSpout kafkaSpout) {
        kafkaspoutMapper.addKafkaSpout(kafkaSpout);
        return true;
    }

    @Override
    public boolean select_batch(String id) {
        if(kafkaspoutMapper.selectKafkaSpout(id)!=null){
            return true;
        }else return false;
    }

    @Override
    public boolean update_batch(KafkaSpout kafkaSpout) {
        kafkaspoutMapper.updateKafkaSpout(kafkaSpout);
        return true;
    }
}
