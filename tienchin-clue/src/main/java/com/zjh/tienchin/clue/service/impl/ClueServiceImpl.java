package com.zjh.tienchin.clue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjh.tienchin.business.domain.Business;
import com.zjh.tienchin.business.service.IBusinessService;
import com.zjh.tienchin.clue.domain.Clue;
import com.zjh.tienchin.clue.domain.vo.ClueDetails;
import com.zjh.tienchin.clue.domain.vo.ClueSummary;
import com.zjh.tienchin.clue.domain.vo.ClueVo;
import com.zjh.tienchin.clue.mapper.ClueMapper;
import com.zjh.tienchin.assignment.service.IAssignmentService;
import com.zjh.tienchin.clue.service.IClueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjh.tienchin.common.constant.TienChinConstants;
import com.zjh.tienchin.common.core.domain.AjaxResult;
import com.zjh.tienchin.common.utils.SecurityUtils;
import com.zjh.tienchin.assignment.domain.Assignment;
import com.zjh.tienchin.follow.domain.FollowRecord;
import com.zjh.tienchin.follow.service.IFollowRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-17
 */
@Service
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue> implements IClueService {
    @Autowired
    IAssignmentService assignmentService;
    @Autowired
    ClueMapper clueMapper;

    @Autowired
    IBusinessService businessService;

    @Autowired
    IFollowRecordService followRecordService;

    @Override
    @Transactional
    public AjaxResult addClue(Clue clue) {
        QueryWrapper<Clue> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Clue::getPhone, clue.getPhone());
        Clue one = getOne(wrapper);
        if (one != null) {
            return new AjaxResult().error("手机号码重复，线索录入失败");
        }
        clue.setDelFlag(0);
        clue.setNextTime(LocalDateTime.now().plusHours(TienChinConstants.NEXT_FOLLOW_TIME));
        clue.setCreateBy(SecurityUtils.getUsername());
        clue.setCreateTime(LocalDateTime.now());
        try {
            // 添加线索
            save(clue);
            // 添加线索默认的分配人
            Assignment assignment = new Assignment();
            assignment.setAssignId(clue.getClueId());
            assignment.setType(TienChinConstants.CLUE_TYPE);
            assignment.setLatest(true);
            assignment.setUserId(SecurityUtils.getUserId());
            assignment.setUserName(SecurityUtils.getUsername());
            assignment.setDeptId(SecurityUtils.getDeptId());
            assignment.setCreateBy(SecurityUtils.getUsername());
            assignment.setCreateTime(LocalDateTime.now());
            assignmentService.save(assignment);
            return AjaxResult.success("线索录入成功");

        } catch (Exception e) {
            return AjaxResult.error("线索录入失败");
        }

    }

    @Override
    public List<ClueSummary> selectClueList(ClueVo clueVO) {
        List<ClueSummary> clueSummaryList = clueMapper.selectClueList(clueVO);
        return clueSummaryList;
    }

    @Override
    public ClueSummary getClueSummaryById(Long clueId) {
        ClueSummary clueSummary = clueMapper.getClueSummaryById(clueId);
        return clueSummary;
    }

    @Override
    public AjaxResult getClueDetailsByClueId(Long clueId) {
        ClueDetails clueDetails = clueMapper.getClueDetailsByClueId(clueId);
        return AjaxResult.success(clueDetails);
    }

    @Override
    @Transactional
    public AjaxResult clueFollow(ClueDetails clueDetails) {
        try {
            Clue clue = new Clue();
            BeanUtils.copyProperties(clueDetails,clue);
            clue.setStatus(TienChinConstants.CLUE_FOLLOWING);
            this.updateById(clue);
            // 前端跟进之后后端保存
            FollowRecord followRecord = new FollowRecord();
            followRecord.setType(TienChinConstants.CLUE_TYPE);
            followRecord.setAssignId(clueDetails.getClueId());
            followRecord.setInfo(clueDetails.getRecord());
            followRecord.setCreateTime(LocalDateTime.now());
            followRecord.setCreateBy(SecurityUtils.getUsername());
            followRecordService.save(followRecord);
            return AjaxResult.success("线索跟进成功");
        } catch (BeansException e) {
            return AjaxResult.error("跟进失败" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public AjaxResult invalidClueFollow(ClueDetails clueDetails) {
        try {
            // 如果已经失败了三次了，则这条线索直接变为伪线索
            Clue c = getById(clueDetails.getClueId());
            if (c.getFailCount() == 3) {
                // 无效线索次数已达极限
                UpdateWrapper<Clue> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().set(Clue::getStatus, TienChinConstants.CLUE_INVALIDATE)
                        .set(Clue::getEndTime, LocalDateTime.now()).set(Clue::getUpdateTime, LocalDateTime.now())
                        .set(Clue::getUpdateBy, SecurityUtils.getUsername())
                        .eq(Clue::getClueId, clueDetails.getClueId());
                update(updateWrapper);
                return AjaxResult.success("无效线索设置成功");
            }
            // 1. 首先需要设置线索表中的 fail_count 字段+1
            UpdateWrapper<Clue> uw = new UpdateWrapper<>();
            uw.lambda().setSql("fail_count=fail_count+1").set(Clue::getUpdateTime, LocalDateTime.now())
                    .set(Clue::getUpdateBy, SecurityUtils.getUsername()).eq(Clue::getClueId, clueDetails.getClueId());
            update(uw);
            // 2。需要往线索记录表中添加一条记录
            FollowRecord record = new FollowRecord();
            record.setInfo(clueDetails.getRecord());
            record.setType(TienChinConstants.CLUE_TYPE);
            record.setCreateTime(LocalDateTime.now());
            record.setCreateBy(SecurityUtils.getUsername());
            record.setAssignId(clueDetails.getClueId());
            followRecordService.save(record);
            return AjaxResult.success("无效线索设置成功");
        } catch (Exception e) {
            return AjaxResult.error("设置失败：" + e.getMessage());
        }
    }

    @Override
    public AjaxResult getClueSummaryByClueId(Long clueId) {
        Clue clue = getById(clueId);
        return AjaxResult.success(clue);
    }

    @Override
    public AjaxResult updateClue(Clue clue) {
        clue.setUpdateBy(SecurityUtils.getUsername());
        clue.setUpdateTime(LocalDateTime.now());
        return updateById(clue) ? AjaxResult.success("更新成功") : AjaxResult.error("更新失败");
    }

    @Override
    public AjaxResult clue2Business(Integer clueId) {
        try {
            Clue clue = getById(clueId);
            Business business = new Business();
            BeanUtils.copyProperties(clue, business);
            business.setCreateBy(SecurityUtils.getUsername());
            business.setCreateTime(LocalDateTime.now());
            business.setEndTime(null);
            business.setFailCount(0);
            business.setNextTime(LocalDateTime.now().plusHours(TienChinConstants.NEXT_FOLLOW_TIME));
            business.setRemark(null);
            business.setUpdateBy(null);
            business.setUpdateTime(null);
            business.setStatus(TienChinConstants.BUSINESS_ALLOCATED);
            business.setClueId(clueId);
            business.setDelFlag(0);
            //删除线索
            UpdateWrapper<Clue> wrapper = new UpdateWrapper<>();
            wrapper.lambda().set(Clue::getDelFlag,1).eq(Clue::getClueId,clueId);
            update(wrapper);
            //添加商机
            businessService.save(business);
            //默认情况下将商机分配给admin，再由admin分配给不同客户专员
            Assignment assignment = new Assignment();
            assignment.setType(TienChinConstants.BUSINESS_TYPE);
            assignment.setUserName(TienChinConstants.AMDIN_USERNAME);
            assignment.setAssignId(business.getBusinessId());
            assignment.setUserId(TienChinConstants.ADMIN_ID);
            assignment.setDeptId(TienChinConstants.ADMIN_DEPT_ID);
            assignment.setCreateBy(SecurityUtils.getUsername());
            assignment.setCreateTime(LocalDateTime.now());
            assignment.setLatest(true);
            assignmentService.save(assignment);
            return AjaxResult.success("线索成功hasPermission商机");
        } catch (BeansException e) {
            //throw new RuntimeException(e);
        }
        return AjaxResult.error("转换失败");
    }
}