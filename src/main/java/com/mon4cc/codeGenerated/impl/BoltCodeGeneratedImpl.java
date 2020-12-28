package com.mon4cc.codeGenerated.impl;

import com.mon4cc.codeGenerated.IBoltCodeGenerated;
import com.mon4cc.entity.Bolt;
import com.mon4cc.service.IBoltService;
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
    IBoltService iBoltService ;

    @Autowired
    BoltTemplate boltTemplate ;


    @Override
    public boolean boltCodeGenerated(String topologyId) {
        int boltCount = iBoltService.boltCount(topologyId) ;

        List<Bolt> lists = iBoltService.selectBoltByTopologyId(topologyId) ;

        for(Bolt bolt : lists){
            boltTemplate = new BoltTemplate(topologyId,bolt) ;
            boltTemplate.classGenerate() ;
        }
        return true;
    }
}
