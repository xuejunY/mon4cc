package com.mon4cc.database.service.impl;

import com.mon4cc.database.entity.Spout;
import com.mon4cc.database.mapper.SpoutMapper;
import com.mon4cc.database.service.ISpoutService;
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
public class SpoutServiceImpl implements ISpoutService {

    @Autowired
    private SpoutMapper spoutMapper;

    @Override
    public boolean insert_batch(Spout spout) {
        spoutMapper.insertSpout(spout);
        return true;
    }

    @Override
    public boolean select_batch(String id) {
        if(spoutMapper.selectSpout(id)!=null){
            return true;
        }else return false;
    }

    @Override
    public boolean update_batch(Spout spout) {
        spoutMapper.updateSpout(spout);
        return true;
    }
}
