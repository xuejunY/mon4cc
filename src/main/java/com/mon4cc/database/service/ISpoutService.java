package com.mon4cc.database.service;

import com.mon4cc.database.entity.Spout;

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

    boolean select_batch(String id);

    boolean update_batch(Spout spout);
}
