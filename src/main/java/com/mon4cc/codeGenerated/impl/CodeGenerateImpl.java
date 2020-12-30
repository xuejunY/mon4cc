package com.mon4cc.codeGenerated.impl;

import com.mon4cc.codeGenerated.*;
import com.mon4cc.service.IKafkaspoutService;
import com.mon4cc.service.ISpoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeGenerateImpl implements ICodeGenerate {

    @Autowired
    IKafkaSpoutCodeGenerated iKafkaSpoutCodeGenerated ;

    @Autowired
    ISpoutCodeGenerated iSpoutCodeGenerated ;

    @Autowired
    IBoltCodeGenerated iBoltCodeGenerated ;

    @Autowired
    ITopologyConfigurationGenerated iTopologyConfigurationGenerated ;

    @Autowired
    ISpoutService iSpoutService ;

    @Autowired
    IKafkaspoutService iKafkaspoutService ;


    @Override
    public boolean generateCode(String topologyId) {
        //there isn't spout
        if(iSpoutService.selectSpouts(topologyId).isEmpty()){
            iKafkaSpoutCodeGenerated.kafkaSpoutCodeGenerated(topologyId) ;
            iBoltCodeGenerated.boltCodeGenerated(topologyId) ;
            iTopologyConfigurationGenerated.topologyConfigurationGenerated(topologyId) ;
            return true ;
        } else if(iKafkaspoutService.selectKafkaSpouts(topologyId).isEmpty()){ //there isn't kafka spout
            iSpoutCodeGenerated.spoutCodeGenerated(topologyId) ;
            iBoltCodeGenerated.boltCodeGenerated(topologyId) ;
            iTopologyConfigurationGenerated.topologyConfigurationGenerated(topologyId) ;
            return true ;
        } else {
            iKafkaSpoutCodeGenerated.kafkaSpoutCodeGenerated(topologyId);
            iSpoutCodeGenerated.spoutCodeGenerated(topologyId);
            iBoltCodeGenerated.boltCodeGenerated(topologyId);
            iTopologyConfigurationGenerated.topologyConfigurationGenerated(topologyId);
            return true;
        }
    }
}
