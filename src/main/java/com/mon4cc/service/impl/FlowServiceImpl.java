package com.mon4cc.service.impl;

import com.mon4cc.entity.Flow;
import com.mon4cc.mapper.FlowMapper;
import com.mon4cc.service.IFlowService;


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
public class FlowServiceImpl implements IFlowService {
    @Autowired
    private FlowMapper groupingMapper;
    @Override
    public boolean insert_batch(Flow grouping1) {
        groupingMapper.insertGrouping(grouping1);
        return true;
    }

    @Override
    public boolean select_batch(String groupingId) {
        if(groupingMapper.selectGrouping(groupingId)!=null){
            return true;
        }else return false;
    }

    @Override
    public boolean update_batch(Flow grouping) {
        groupingMapper.updateGrouping(grouping);
        return true;
    }
}

