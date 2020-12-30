package com.mon4cc.service.impl;

import com.mon4cc.entity.KafkaSpout;
import com.mon4cc.mapper.KafkaspoutMapper;
import com.mon4cc.service.IKafkaspoutService;

import org.apache.logging.log4j.core.appender.mom.kafka.KafkaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public boolean select_batch(String id,String topologyId) {
        if(kafkaspoutMapper.selectKafkaSpout(id,topologyId)!=null){
            return true;
        }else return false;
    }

    @Override
    public boolean update_batch(KafkaSpout kafkaSpout) {
        kafkaspoutMapper.updateKafkaSpout(kafkaSpout);
        return true;
    }

    @Override
    public List<KafkaSpout> selectKafkaSpouts(String topologyId) {
        return kafkaspoutMapper.getAllKafkaSpouts(topologyId);
    }

    @Override
    public boolean updateCode(String id, String topologyId, String code) {
        return kafkaspoutMapper.updatekafkaSpoutCodeIntoSpoutTable(id, topologyId, code);
    }
}
