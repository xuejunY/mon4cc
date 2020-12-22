package com.mon4cc.database.mapper;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangsanhe
 * @since 2020-12-19
 */
@Service
public interface TopologyconfigurationMapper{

    int addXml(String tid, String modelXml);
}
