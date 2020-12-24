package com.mon4cc.mapper;

import org.apache.ibatis.annotations.Param;
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

    int addXml(@Param("tid") String tid, @Param("modelXml") String modelXml);
    String getXml(@Param("tid") String tid);
}
