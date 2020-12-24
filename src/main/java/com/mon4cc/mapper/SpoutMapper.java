package com.mon4cc.mapper;


import com.mon4cc.entity.Spout;
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
public interface SpoutMapper{

    void insertSpout(Spout spout);

    Spout selectSpout(@Param("id") String id);

    boolean updateSpout(Spout spout);
}
