package com.mon4cc.mapper;

import com.mon4cc.entity.Flow;
import com.mon4cc.entity.KafkaSpout;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangsanhe
 * @since 2020-12-19
 */
@Service
public interface FlowMapper {

    int insertGrouping(Flow grouping);

    Flow selectGrouping(@Param("groupingId") String groupingId,@Param("topologyId") String topologyId);

    int updateGrouping(Flow grouping);

    String selectGroupingId(@Param("targetComponent")String targetComponent,@Param("topologyId") String topologyId) ;

    List<Flow> getAllFlows(@Param("topologyId") String topologyId) ;
}
