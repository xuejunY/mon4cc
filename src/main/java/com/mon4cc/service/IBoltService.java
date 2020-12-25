package com.mon4cc.service;

import com.mon4cc.entity.Bolt;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangsanhe
 * @since 2020-12-19
 */

public interface IBoltService{
    boolean insert_batch(Bolt bolt);

    boolean select_batch(String id,String topologyId);

    boolean update_batch(Bolt bolt1);
}
