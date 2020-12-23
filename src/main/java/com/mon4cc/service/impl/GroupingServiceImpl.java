package com.mon4cc.service.impl;

import com.mon4cc.entity.Grouping;
import com.mon4cc.mapper.GroupingMapper;
import com.mon4cc.service.IGroupingService;
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
public class GroupingServiceImpl implements IGroupingService {
    @Autowired
    private GroupingMapper groupingMapper;
    @Override
    public boolean insert_batch(Grouping grouping1) {
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
    public boolean update_batch(Grouping grouping) {
        groupingMapper.updateGrouping(grouping);
        return true;
    }
}
