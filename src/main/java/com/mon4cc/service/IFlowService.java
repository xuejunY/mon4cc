package com.mon4cc.service;

import com.mon4cc.entity.Flow;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangsanhe
 * @since 2020-12-19
 */
public interface IFlowService {

    boolean insert_batch(Flow grouping1);

    boolean select_batch(String groupingId,String topologyId);

    boolean update_batch(Flow grouping1);

    Flow selectFlow(String groupingId,String topologyId) ;

    String getFlowIdByTarget(String targetComponent) ;

}
