package com.mon4cc.database.service.impl;

import com.mon4cc.database.mapper.TopologyconfigurationMapper;
import com.mon4cc.database.service.ITopologyconfigurationService;
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
public class TopologyconfigurationServiceImpl implements ITopologyconfigurationService {
    @Autowired
    private TopologyconfigurationMapper topologyconfigurationMapper;

    @Override
    public boolean insertXml(String tid, String modelXml) {
        topologyconfigurationMapper.addXml(tid,modelXml);
        return true;
    }
}
