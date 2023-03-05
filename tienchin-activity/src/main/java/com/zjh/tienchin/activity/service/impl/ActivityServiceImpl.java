package com.zjh.tienchin.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjh.tienchin.activity.domain.Activity;
import com.zjh.tienchin.activity.mapper.ActivityMapper;
import com.zjh.tienchin.activity.service.IActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjh.tienchin.activity.domain.vo.ActivityVO;
import com.zjh.tienchin.common.core.domain.AjaxResult;
import com.zjh.tienchin.common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-15
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Autowired
    ActivityMapper activityMapper;

    @Override
    public AjaxResult addActivity(ActivityVO activityVO) {
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Activity::getChannelId,activityVO.getChannelId()).eq(Activity::getName,activityVO.getName()).eq(Activity::getDelFlag,0);
        Activity c = getOne(wrapper);
        if (c != null) {
            //说明存在一个同名的活动并且没有删除
            return AjaxResult.error("存在同名活动，添加失败");
        }
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityVO,activity);
        activity.setCreateBy(SecurityUtils.getUsername());
        activity.setCreateTime(LocalDateTime.now());
        activity.setDelFlag(0);
        activity.setStatus(1);
        return save(activity)?AjaxResult.success("添加成功"): AjaxResult.error("添加失败");
    }

    @Override
    public List<ActivityVO> selectActivityList(ActivityVO activityVO) {
        expireActivity();
        return activityMapper.selectActivityList(activityVO);
    }

    @Override
    public AjaxResult updateActivity(ActivityVO activityVO) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityVO, activity);
        activity.setUpdateTime(LocalDateTime.now());
        activity.setUpdateBy(SecurityUtils.getUsername());
        activity.setCreateTime(null);
        activity.setCreateBy(null);
        activity.setDelFlag(null);
        return updateById(activity)?AjaxResult.success("更新成功"): AjaxResult.error("更新失败");

    }

    @Override
    public ActivityVO getActivityById(Long activityId) {
        Activity activity = getById(activityId);
        ActivityVO activityVO = new ActivityVO();
        BeanUtils.copyProperties(activity, activityVO);
        return activityVO;
    }

    @Override
    public Boolean deleteActivityByIds(Long[] activityIds) {
        UpdateWrapper<Activity> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(Activity::getDelFlag,1).in(Activity::getActivityId,activityIds);
        return update(wrapper);
    }

    @Override
    public AjaxResult selectActivityByChannelId(Integer channelId) {
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Activity:: getChannelId,channelId);
        return AjaxResult.success(list(wrapper));
    }



    public boolean expireActivity(){
        UpdateWrapper<Activity> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(Activity::getStatus,0).eq(Activity::getStatus,1).lt(Activity::getEndTime,LocalDateTime.now());
        return update(wrapper);
    }
}
