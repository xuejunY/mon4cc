package com.mon4cc.service.impl;

import com.mon4cc.entity.Bolt;
import com.mon4cc.mapper.BoltMapper;
import com.mon4cc.service.IBoltService;



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
public class BoltServiceImpl implements IBoltService {

    @Autowired
    private BoltMapper boltMapper;

    @Override
    public boolean insert_batch(Bolt bolt) {
        boltMapper.insertBolt(bolt);
        return true;
    }

    @Override
    public boolean select_batch(String id) {
        if(boltMapper.selectBolt(id)!=null){
            return true;
        }else return false;
    }

    @Override
    public boolean update_batch(Bolt bolt) {
        boltMapper.updateBolt(bolt);
        return true;
    }
}
