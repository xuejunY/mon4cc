package com.mon4cc.codeGenerated.impl;

import com.mon4cc.codeGenerated.ITopologyConfigurationGenerated;
import com.mon4cc.entity.Bolt;
import com.mon4cc.entity.Flow;
import com.mon4cc.entity.KafkaSpout;
import com.mon4cc.entity.Spout;
import com.mon4cc.service.*;
import com.mon4cc.template.ComfigurationTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    IModelconfigurationService iModelconfigurationService;

    @Autowired
    IFlowService iFlowService ;

    @Override
    public boolean topologyConfigurationGenerated(String topologyId) {
        List<Spout> spouts = iSpoutService.selectSpouts(topologyId) ;
        List<KafkaSpout> kafkaSpouts = iKafkaspoutService.selectKafkaSpouts(topologyId) ;
        List<Bolt> bolts = iBoltService.selectBoltByTopologyId(topologyId) ;
        List<Flow> flows = iFlowService.selectFlows(topologyId) ;
        comfigurationTemplate.setSpouts(spouts) ;
        comfigurationTemplate.setBolts(bolts) ;
        comfigurationTemplate.setKafkaSpouts(kafkaSpouts) ;
        comfigurationTemplate.setFlows(flows);
        iModelconfigurationService.updateCode(topologyId,comfigurationTemplate.generateClassText(iModelconfigurationService.getTopologyName(topologyId),
                iModelconfigurationService.getIsLocal(topologyId))) ;
        return true;
    }
}
