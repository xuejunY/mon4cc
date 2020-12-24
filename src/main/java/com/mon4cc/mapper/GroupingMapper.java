package com.mon4cc.mapper;

import com.mon4cc.entity.Grouping;
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
public interface GroupingMapper{

    int insertGrouping(Grouping grouping);

    Grouping selectGrouping(String groupingId);

    int updateGrouping(Grouping grouping);
}
