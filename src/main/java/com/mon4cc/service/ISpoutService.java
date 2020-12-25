package com.mon4cc.service;

import com.mon4cc.entity.Spout;

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
}
