package com.mon4cc.codeGenerated.impl;

import com.mon4cc.codeGenerated.ISpoutCodeGenerated;
import com.mon4cc.entity.Spout;
import com.mon4cc.service.ISpoutService;
import com.mon4cc.service.IModelconfigurationService;
import com.mon4cc.template.SpoutTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xjyou_
 * @Date 2020.12.30
 */
@Service
public class SpoutCodeGeneratedImpl implements ISpoutCodeGenerated {

    @Autowired
    private ISpoutService iSpoutService ;

    @Autowired
    private IModelconfigurationService iModelconfigurationService;

    @Autowired
    private SpoutTemplate spoutTemplate ;

    @Override
    public boolean spoutCodeGenerated(String topologyId) {
        List<Spout> spouts = iSpoutService.selectSpouts(topologyId) ;
        for(Spout spout : spouts){
            spoutTemplate.setSpout(spout) ;
            String code = spoutTemplate.generateClassText(iModelconfigurationService.getTopologyName(topologyId),
                    spout.getSpoutComponentName()) ;
            iSpoutService.updateCode(spout.getId(), topologyId, code) ;
        }
        return true;
    }
}
