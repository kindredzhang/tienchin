package com.zjh.tienchin.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjh.tienchin.assignment.service.IAssignmentService;
import com.zjh.tienchin.business.domain.Business;
import com.zjh.tienchin.business.domain.vo.BusinessFollow;
import com.zjh.tienchin.business.domain.vo.BusinessSummary;
import com.zjh.tienchin.business.domain.vo.BusinessSummaryEnhance;
import com.zjh.tienchin.business.domain.vo.BusinessVO;
import com.zjh.tienchin.business.mapper.BusinessMapper;
import com.zjh.tienchin.business.service.IBusinessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjh.tienchin.common.constant.TienChinConstants;
import com.zjh.tienchin.common.core.domain.AjaxResult;
import com.zjh.tienchin.common.utils.SecurityUtils;
import com.zjh.tienchin.assignment.domain.Assignment;
import com.zjh.tienchin.follow.domain.FollowRecord;
import com.zjh.tienchin.follow.service.IFollowRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kindredzhang
 * @since 2023-03-03
 */
@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements IBusinessService {

    @Autowired
    BusinessMapper  businessMapper;
    @Autowired
    IAssignmentService assignmentService;

    @Autowired
    IFollowRecordService followRecordService;
    @Override
    public List<BusinessSummary> selectBusinessList(BusinessVO businessVO) {
        List<BusinessSummary> businessSummaryList = businessMapper.selectBusinessList(businessVO);
        return businessSummaryList;
    }

    @Override
    public AjaxResult addBusiness(Business business) {
        QueryWrapper<Business> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Business::getPhone,business.getPhone());
        if (getOne(wrapper)!=null){
            return AjaxResult.error("客户手机号码重复，录入失败");
        }
        try {
            business.setStatus(TienChinConstants.BUSINESS_ALLOCATED);
            business.setCreateBy(SecurityUtils.getUsername());
            business.setCreateTime(LocalDateTime.now());
            business.setNextTime(LocalDateTime.now().plusHours(TienChinConstants.NEXT_FOLLOW_TIME));
            save(business);
            Assignment assignment = new Assignment();
            assignment.setType(TienChinConstants.BUSINESS_TYPE);
            assignment.setAssignId(business.getBusinessId());
            assignment.setUserId(SecurityUtils.getUserId());
            assignment.setUserName(SecurityUtils.getUsername());
            assignment.setDeptId(SecurityUtils.getDeptId());
            assignment.setLatest(true);
            assignment.setCreateBy(SecurityUtils.getUsername());
            assignment.setCreateTime(LocalDateTime.now());
            assignmentService.save(assignment);
            return AjaxResult.success("商机录入成功");
        } catch (Exception e) {
            return AjaxResult.error("商机录入失败"+e.getMessage());
        }
    }

    @Override
    public AjaxResult getBusinessSummaryByBusinessId(Integer businessId) {
        Business business = getById(businessId);
        BusinessSummaryEnhance businessSummaryEnhance = new BusinessSummaryEnhance();
        BeanUtils.copyProperties(business,businessSummaryEnhance);
        return AjaxResult.success(businessSummaryEnhance);
    }

    @Override
    public AjaxResult updateBusiness(BusinessSummaryEnhance businessSummaryEnhance) {
        Business business = new Business();
        BeanUtils.copyProperties(businessSummaryEnhance,business);
        return updateById(business)? AjaxResult.success("更新成功") : AjaxResult.error("更新失败");
    }

    @Override
    public AjaxResult deleteBusinessById(Integer[] businessIds) {
        UpdateWrapper<Business> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(Business::getDelFlag,1).in(Business::getBusinessId,businessIds);
        return update(wrapper)? AjaxResult.success("删除成功") : AjaxResult.error("删除失败");
    }

    @Override
    @Transactional
    public AjaxResult follow(BusinessFollow businessFollow) {
        Business business = new Business();
        BeanUtils.copyProperties(businessFollow, business);
        business.setUpdateBy(SecurityUtils.getUsername());
        business.setUpdateTime(LocalDateTime.now());
        updateById(business);
        //添加跟踪记录
        FollowRecord record = new FollowRecord();
        record.setType(TienChinConstants.BUSINESS_TYPE);
        record.setAssignId(businessFollow.getBusinessId());
        record.setCreateBy(SecurityUtils.getUsername());
        record.setCreateTime(LocalDateTime.now());
        record.setInfo(businessFollow.getInfo());
        followRecordService.save(record);
        return  AjaxResult.success("商机跟进成功");
    }

    @Override
    public AjaxResult getBusinessById(Integer id) {
        return AjaxResult.success(getById(id));
    }
}
