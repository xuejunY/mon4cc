package com.mon4cc.service;

import com.mon4cc.entity.Spout;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangsanhe
 * @since 2020-12-19
 */
public interface ISpoutService{

    boolean insert_batch(Spout spout);

    boolean select_batch(String id,String topologyId);

    boolean update_batch(Spout spout);

    List<Spout> selectSpouts(String topologyId) ;

    boolean updateCode(String id,String topologyId, String code) ;

}
