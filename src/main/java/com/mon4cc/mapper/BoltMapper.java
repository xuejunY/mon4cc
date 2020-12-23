package com.mon4cc.mapper;


import com.mon4cc.entity.Bolt;
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
public interface BoltMapper{

    int insertBolt(Bolt bolt);

    Bolt selectBolt(String  id);

    int updateBolt(Bolt bolt);
}
