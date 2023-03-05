package com.zjh.tienchin.assignment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjh.tienchin.assignment.domain.Assignment;
import com.zjh.tienchin.common.core.domain.AjaxResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-17
 */
public interface IAssignmentService extends IService<Assignment> {
    AjaxResult assignClue(Assignment assignment);
}
