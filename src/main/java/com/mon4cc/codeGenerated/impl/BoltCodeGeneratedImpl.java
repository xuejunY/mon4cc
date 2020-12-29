package com.mon4cc.codeGenerated.impl;

import com.mon4cc.codeGenerated.IBoltCodeGenerated;
import com.mon4cc.entity.Bolt;
import com.mon4cc.entity.Flow;
import com.mon4cc.service.IBoltService;
import com.mon4cc.service.IFlowService;
import com.mon4cc.service.ITopologyconfigurationService;
import com.mon4cc.template.BoltTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class (# BoltCodeGeneratedImpl) is used to generate all bolt code
 */
@Service
public class BoltCodeGeneratedImpl implements IBoltCodeGenerated {

    @Autowired
    private IBoltService iBoltService ;

    @Autowired
    private IFlowService iFlowService ;

    private Flow flow ;


    @Autowired
    private ITopologyconfigurationService iTopologyconfigurationService ;

    @Autowired
    BoltTemplate boltTemplate ;


    @Override
    public boolean boltCodeGenerated(String topologyId) {

        List<Bolt> lists = iBoltService.selectBoltByTopologyId(topologyId) ;
        String topologyName = iTopologyconfigurationService.getTopologyName(topologyId) ;

        for(Bolt bolt : lists){
            //bolt名字；这种方法只适用于一个流进入bolt
            String inGroupingId = iFlowService.getFlowIdByTarget(bolt.getBoltComponentName()) ;
            flow = iFlowService.selectFlow(inGroupingId,topologyId) ;

          boltTemplate.setFlow(flow);

//            boltTemplate =new BoltTemplate(topologyId,bolt,flow,topologyName);
            boltTemplate.setTopologyName(topologyName);
            boltTemplate.setTopologyId(topologyId);
            boltTemplate.setBolt(bolt);

            iBoltService.updateCode(bolt.getId(), topologyId, boltTemplate.classGenerate()) ;
        }
        return true;
    }
}
