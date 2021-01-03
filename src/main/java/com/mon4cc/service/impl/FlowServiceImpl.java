package com.mon4cc.service.impl;

import com.mon4cc.entity.Flow;
import com.mon4cc.mapper.FlowMapper;
import com.mon4cc.service.IFlowService;


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
public class FlowServiceImpl implements IFlowService {
    @Autowired
    private FlowMapper groupingMapper;
    @Override
    public boolean insert_batch(Flow grouping1) {
        groupingMapper.insertGrouping(grouping1);
        return true;
    }

    @Override
    public boolean select_batch(String groupingId,String topologyId) {
        if(groupingMapper.selectGrouping(groupingId,topologyId)!=null){
            return true;
        }else return false;
    }

    @Override
    public boolean update_batch(Flow grouping) {
        groupingMapper.updateGrouping(grouping);
        return true;
    }

    @Override
    public Flow selectFlow(String groupingId,String topologyId) {
        return groupingMapper.selectGrouping(groupingId, topologyId);
    }

    @Override
    public String getFlowIdByTarget(String targetComponent, String topologyId) {
        return groupingMapper.selectGroupingId(targetComponent,topologyId);
    }

    @Override
    public List<String> getFlowIdsByTarget(String targetComponentId, String topologyId) {
        return groupingMapper.selectGroupingsByTargetId(targetComponentId,topologyId);
    }

    @Override
    public List<String> getFlowIdsBySource(String sourceComponentId, String topologyId) {
        return groupingMapper.selectGroupingsBySourceId(sourceComponentId,topologyId);
    }

    @Override
    public Flow selectFlows(String groupingId, String topologyId) {
        return groupingMapper.selectGroupings(groupingId,topologyId);
    }

    @Override
    public List<Flow> selectFlows(String topologyId) {
        return groupingMapper.getAllFlows(topologyId);
    }
}

