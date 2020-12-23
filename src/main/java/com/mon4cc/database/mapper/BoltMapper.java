package com.mon4cc.database.mapper;


import com.mon4cc.database.entity.Bolt;
import org.springframework.context.annotation.ComponentScan;
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
