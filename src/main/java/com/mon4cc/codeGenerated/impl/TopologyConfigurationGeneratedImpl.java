package com.mon4cc.codeGenerated.impl;

import com.mon4cc.codeGenerated.ITopologyConfigurationGenerated;
import com.mon4cc.entity.Bolt;
import com.mon4cc.entity.KafkaSpout;
import com.mon4cc.entity.Spout;
import com.mon4cc.service.IBoltService;
import com.mon4cc.service.IKafkaspoutService;
import com.mon4cc.service.ISpoutService;
import com.mon4cc.service.ITopologyconfigurationService;
import com.mon4cc.template.ComfigurationTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TopologyConfigurationGeneratedImpl implements ITopologyConfigurationGenerated {

    @Autowired
    ISpoutService iSpoutService ;

    @Autowired
    IKafkaspoutService iKafkaspoutService ;

    @Autowired
    IBoltService iBoltService ;

    @Autowired
    ComfigurationTemplate comfigurationTemplate ;

    @Autowired
    ITopologyconfigurationService iTopologyconfigurationService ;

    @Override
    public boolean topologyConfigurationGenerated(String topologyId) {
        List<Spout> spouts = iSpoutService.selectSpouts(topologyId) ;
        List<KafkaSpout> kafkaSpouts = iKafkaspoutService.selectKafkaSpouts(topologyId) ;
        List<Bolt> bolts = iBoltService.selectBoltByTopologyId(topologyId) ;
        comfigurationTemplate.setSpouts(spouts) ;
        comfigurationTemplate.setBolts(bolts) ;
        comfigurationTemplate.setKafkaSpouts(kafkaSpouts) ;
        iTopologyconfigurationService.updateCode(topologyId,comfigurationTemplate.generateClassText(iTopologyconfigurationService.getTopologyName(topologyId),
                iTopologyconfigurationService.getIsLocal(topologyId))) ;
        return true;
    }
}
