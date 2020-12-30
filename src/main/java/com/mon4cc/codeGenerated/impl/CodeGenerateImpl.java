package com.mon4cc.codeGenerated.impl;

import com.mon4cc.codeGenerated.*;
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


    @Override
    public boolean generateCode(String topologyId) {
        iKafkaSpoutCodeGenerated.kafkaSpoutCodeGenerated(topologyId) ;
        iSpoutCodeGenerated.spoutCodeGenerated(topologyId) ;
        iBoltCodeGenerated.boltCodeGenerated(topologyId) ;
        iTopologyConfigurationGenerated.topologyConfigurationGenerated(topologyId) ;
        return true;
    }
}
