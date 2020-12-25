package com.mon4cc.mapper;

import com.mon4cc.entity.Flow;
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
public interface FlowMapper {

    int insertGrouping(Flow grouping);

    Flow selectGrouping(@Param("groupingId") String groupingId,@Param("topologyId") String topologyId);

    int updateGrouping(Flow grouping);
}
