package com.mon4cc.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.mon4cc.entity.Bolt;


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

    Bolt selectBolt(@Param("id") String  id);

    int updateBolt(Bolt bolt);
}
