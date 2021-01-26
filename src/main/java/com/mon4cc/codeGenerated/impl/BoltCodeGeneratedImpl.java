package com.mon4cc.codeGenerated.impl;

import com.mon4cc.codeGenerated.IBoltCodeGenerated;
import com.mon4cc.entity.Bolt;
import com.mon4cc.entity.Flow;
import com.mon4cc.service.IBoltService;
import com.mon4cc.service.IFlowService;
import com.mon4cc.service.IModelconfigurationService;
import com.mon4cc.template.BoltImproveTemplate;
import com.mon4cc.template.BoltTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xjyou_
 * @Date 2020.12.26
 */
@Service
public class BoltCodeGeneratedImpl implements IBoltCodeGenerated {

    @Autowired
    private IBoltService iBoltService ;

    @Autowired
    private IFlowService iFlowService ;

    private Flow flow ;
    private List<Flow> inFlows ;
    private List<Flow> outFlows ;

    @Autowired
    private IModelconfigurationService iModelconfigurationService;

    @Autowired
    private BoltTemplate boltTemplate ;

    @Autowired
    private BoltImproveTemplate boltImproveTemplate ;


    @Override
    public boolean boltCodeGenerated(String topologyId) {

        List<Bolt> lists = iBoltService.selectBoltByTopologyId(topologyId) ;
        String topologyName = iModelconfigurationService.getTopologyName(topologyId) ;

        for(Bolt bolt : lists){
            //The way only permit one flow in and out bolt

            String inGroupingId = iFlowService.getFlowIdByTarget(bolt.getId(),topologyId) ;
            flow = iFlowService.selectFlow(inGroupingId,topologyId) ;

            boltTemplate.setFlow(flow);
            boltTemplate.setTopologyName(topologyName);
            boltTemplate.setTopologyId(topologyId);
            boltTemplate.setBolt(bolt);

            iBoltService.updateCode(bolt.getId(), topologyId, boltTemplate.classGenerate()) ;
        }
        return true;
    }

    @Override
    public boolean boltCodeGeneratedUpgraded(String topologyId) {
        List<Bolt> lists = iBoltService.selectBoltByTopologyId(topologyId) ;
        String topologyName = iModelconfigurationService.getTopologyName(topologyId) ;
        // The way  permit one or two flow in and out bolt
        for(Bolt bolt : lists){
            //find one or more one flow
            List<String> inGroupingIds = iFlowService.getFlowIdsBySource(bolt.getId(),topologyId) ;
            inFlows = new ArrayList<>() ;
            for(String inGroupingId : inGroupingIds) inFlows.add(iFlowService.selectFlows(inGroupingId,topologyId)) ;
            boltImproveTemplate.setInFlows(inFlows);
            List<String> outGroupingIds = iFlowService.getFlowIdsByTarget(bolt.getId(),topologyId) ;
            outFlows = new ArrayList<>() ;
            for (String outGroupingId :outGroupingIds) outFlows.add(iFlowService.selectFlows(outGroupingId,topologyId)) ;
            boltImproveTemplate.setOutFlows(outFlows);
            boltImproveTemplate.setBolt(bolt);
            iBoltService.updateCode(bolt.getId(), topologyId, boltImproveTemplate.generateClassText(topologyName)) ;
        }
        return true;
    }

}
