package com.mon4cc.mapper;



import com.mon4cc.entity.Bolt;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangsanhe
 * @since 2020-12-19
 */
@Service
public interface BoltMapper{

    int insertBolt(Bolt bolt);

    Bolt selectBolt(@Param("id") String id,@Param("topologyId") String topologyId);

    int updateBolt(Bolt bolt);

    /**
     *
     * @param topologyId
     * @return Bolt
     */
    Bolt selectBoltForCode(@Param("topologyId") String topologyId) ;
}
