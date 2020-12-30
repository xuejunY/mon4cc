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
    String getTopologyName(@Param("tid")String tid) ;
    boolean getIsLocal(@Param("topologyId")String topologyId) ;
    boolean updateConfigurationCodeIntoSpoutTable(@Param("topologyId")String topologyId,@Param("completeConfigurationCode") String completeConfigurationCode);
}
