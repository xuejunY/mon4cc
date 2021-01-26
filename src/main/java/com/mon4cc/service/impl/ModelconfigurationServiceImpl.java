package com.mon4cc.service.impl;


import com.mon4cc.mapper.ModelconfigurationMapper;
import com.mon4cc.service.IModelconfigurationService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangsanhe
 * @since 2020-12-19
 */
@Service
public class ModelconfigurationServiceImpl implements IModelconfigurationService {
    @Autowired
    private ModelconfigurationMapper modelconfigurationMapper;

    @Override
    public boolean insertXml(String tid, String modelXml) {
        modelconfigurationMapper.addXml(tid,modelXml);
        return true;
    }

    @Override
    public String selectXml(String tid) {
        return  modelconfigurationMapper.getXml(tid);

    }

    @Override
    public String getTopologyName(String tid) {
        return modelconfigurationMapper.getTopologyName(tid) ;
    }

    @Override
    public boolean getIsLocal(String topologyId) {
        return modelconfigurationMapper.getIsLocal(topologyId);
    }

    @Override
    public boolean updateCode(String topologyId, String code) {
        return modelconfigurationMapper.updateConfigurationCodeIntoSpoutTable(topologyId,code);
    }
}

