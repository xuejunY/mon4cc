package com.mon4cc.service.impl;

import com.mon4cc.entity.Bolt;
import com.mon4cc.mapper.BoltMapper;
import com.mon4cc.service.IBoltService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangsanhe
 * @since 2020-12-19
 */
@Service
public class BoltServiceImpl implements IBoltService {

    @Autowired
    private BoltMapper boltMapper;

    @Override
    public boolean insert_batch(Bolt bolt) {
        boltMapper.insertBolt(bolt);
        return true;
    }

    @Override
    public boolean select_batch(String id,String topologyId) {
        if(boltMapper.selectBolt(id,topologyId)!=null){
            return true;
        }else return false;
    }

    @Override
    public boolean update_batch(Bolt bolt) {
        boltMapper.updateBolt(bolt);
        return true;
    }

    @Override
    public Bolt selectBolt(String topologyId) {
        return boltMapper.selectBoltForCode(topologyId);
    }

    /**
     * select bolt list from bolt table base on topologyId
     * @param topologyId
     * @return
     * @author xjyou_
     */
    @Override
    public List<Bolt> selectBoltByTopologyId(String topologyId) {
        return boltMapper.selectBoltById(topologyId);
    }

    @Override
    public boolean updateCode(String id, String topologyId, String code) {
        return boltMapper.updateCodeIntoBoltTable(id, topologyId, code);
    }
}
