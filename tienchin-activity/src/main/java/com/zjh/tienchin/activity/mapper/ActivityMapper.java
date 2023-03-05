package com.zjh.tienchin.activity.mapper;

import com.zjh.tienchin.activity.domain.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjh.tienchin.activity.domain.vo.ActivityVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-15
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    List<ActivityVO> selectActivityList(ActivityVO activityVO);
}
