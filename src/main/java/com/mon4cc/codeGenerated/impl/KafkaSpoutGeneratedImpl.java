package com.mon4cc.codeGenerated.impl;

import com.mon4cc.codeGenerated.IKafkaSpoutCodeGenerated;
import com.mon4cc.entity.KafkaSpout;
import com.mon4cc.entity.TopologyConfiguration;
import com.mon4cc.service.IKafkaspoutService;
import com.mon4cc.service.ITopologyconfigurationService;
import com.mon4cc.template.KafkaSpoutTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaSpoutGeneratedImpl implements IKafkaSpoutCodeGenerated {

    @Autowired
    IKafkaspoutService iKafkaspoutService ;

    @Autowired
    KafkaSpoutTemplate kafkaSpoutTemplate ;

    @Autowired
    ITopologyconfigurationService iTopologyconfigurationService ;

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
            topologyName = iTopologyconfigurationService.getTopologyName(topologyId) ;
            kafkaSpoutName = kafkaSpout.getSpoutComponentName() ;
            kafkaSpoutId = kafkaSpout.getId() ;
            kafkaSpoutCode  = kafkaSpoutTemplate.generateClassText(topologyName,kafkaSpoutName) ;
            flag = iKafkaspoutService.updateCode(kafkaSpoutId,topologyId,kafkaSpoutCode) ;
        }
        return true;
    }
}
