package com.mon4cc.database.service;

import com.mon4cc.database.entity.Grouping;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangsanhe
 * @since 2020-12-19
 */
public interface IGroupingService{

    boolean insert_batch(Grouping grouping1);

    boolean select_batch(String groupingId);

    boolean update_batch(Grouping grouping1);
}
