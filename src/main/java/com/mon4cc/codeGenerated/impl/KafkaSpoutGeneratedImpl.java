package com.mon4cc.codeGenerated.impl;

import com.mon4cc.codeGenerated.IKafkaSpoutCodeGenerated;
import com.mon4cc.entity.KafkaSpout;
import com.mon4cc.service.IKafkaspoutService;
import com.mon4cc.service.IModelconfigurationService;
import com.mon4cc.template.KafkaSpoutTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xjyou_
 * @Date 2020.12.30
 */
@Service
public class KafkaSpoutGeneratedImpl implements IKafkaSpoutCodeGenerated {

    @Autowired
    IKafkaspoutService iKafkaspoutService ;

    @Autowired
    KafkaSpoutTemplate kafkaSpoutTemplate ;

    @Autowired
    IModelconfigurationService iModelconfigurationService;

    String topologyName ;
    String kafkaSpoutName ;
    String kafkaSpoutCode ;
    String kafkaSpoutId ;
    boolean flag ;
    @Override
    public boolean kafkaSpoutCodeGenerated(String topologyId) {
        List<KafkaSpout> kafkaSpouts = iKafkaspoutService.selectKafkaSpouts(topologyId) ;
        for(KafkaSpout kafkaSpout : kafkaSpouts){
            kafkaSpoutTemplate.setKafkaSpout(kafkaSpout);
            topologyName = iModelconfigurationService.getTopologyName(topologyId) ;
            kafkaSpoutName = kafkaSpout.getSpoutComponentName() ;
            kafkaSpoutId = kafkaSpout.getId() ;
            kafkaSpoutCode  = kafkaSpoutTemplate.generateClassText(topologyName,kafkaSpoutName) ;
            flag = iKafkaspoutService.updateCode(kafkaSpoutId,topologyId,kafkaSpoutCode) ;
        }
        return true;
    }
}
