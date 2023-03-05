package com.zjh.tienchin.business.service;

import com.zjh.tienchin.business.domain.Business;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjh.tienchin.business.domain.vo.BusinessFollow;
import com.zjh.tienchin.business.domain.vo.BusinessSummary;
import com.zjh.tienchin.business.domain.vo.BusinessSummaryEnhance;
import com.zjh.tienchin.business.domain.vo.BusinessVO;
import com.zjh.tienchin.common.core.domain.AjaxResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kindredzhang
 * @since 2023-03-03
 */
public interface IBusinessService extends IService<Business> {

    List<BusinessSummary> selectBusinessList(BusinessVO businessVO);

    AjaxResult addBusiness(Business business);

    AjaxResult getBusinessSummaryByBusinessId(Integer businessId);

    AjaxResult updateBusiness(BusinessSummaryEnhance businessSummaryEnhance);

    AjaxResult deleteBusinessById(Integer[] businessIds);

    AjaxResult follow(BusinessFollow businessFollow);

    AjaxResult getBusinessById(Integer id);
}
