package com.zjh.tienchin.follow.service;

import com.zjh.tienchin.common.core.domain.AjaxResult;
import com.zjh.tienchin.follow.domain.FollowRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kindredzhang
 * @since 2023-03-04
 */
public interface IFollowRecordService extends IService<FollowRecord> {


    AjaxResult getFollowRecordByClueId(Integer clueId);

    AjaxResult getFollowRecordByBusinessId(Integer businessId);
}
