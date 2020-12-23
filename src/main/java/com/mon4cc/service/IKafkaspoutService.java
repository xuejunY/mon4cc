package com.mon4cc.service;

import com.mon4cc.entity.KafkaSpout;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangsanhe
 * @since 2020-12-19
 */
public interface IKafkaspoutService{

    boolean insert_batch(KafkaSpout kafkaSpout);

    boolean select_batch(String id);

    boolean update_batch(KafkaSpout kafkaSpout);
}
