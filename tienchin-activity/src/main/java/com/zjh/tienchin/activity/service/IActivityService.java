package com.zjh.tienchin.activity.service;

import com.zjh.tienchin.activity.domain.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjh.tienchin.activity.domain.vo.ActivityVO;
import com.zjh.tienchin.common.core.domain.AjaxResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-15
 */
public interface IActivityService extends IService<Activity> {

    AjaxResult addActivity(ActivityVO activityVO);

    List<ActivityVO> selectActivityList(ActivityVO activityVO);

    AjaxResult updateActivity(ActivityVO activityVO);

    ActivityVO getActivityById(Long activityId);

    Boolean deleteActivityByIds(Long[] activityIds);


    AjaxResult selectActivityByChannelId(Integer channelId);

}
