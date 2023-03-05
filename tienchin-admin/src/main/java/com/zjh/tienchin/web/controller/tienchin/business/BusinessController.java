package com.zjh.tienchin.web.controller.tienchin.business;

import com.zjh.tienchin.activity.service.IActivityService;
import com.zjh.tienchin.business.domain.Business;
import com.zjh.tienchin.business.domain.vo.BusinessFollow;
import com.zjh.tienchin.business.domain.vo.BusinessSummary;
import com.zjh.tienchin.business.domain.vo.BusinessSummaryEnhance;
import com.zjh.tienchin.business.domain.vo.BusinessVO;
import com.zjh.tienchin.business.service.IBusinessService;
import com.zjh.tienchin.channel.service.IChannelService;
import com.zjh.tienchin.common.annotation.Log;
import com.zjh.tienchin.common.core.controller.BaseController;
import com.zjh.tienchin.common.core.domain.AjaxResult;
import com.zjh.tienchin.common.core.page.TableDataInfo;
import com.zjh.tienchin.common.enums.BusinessType;
import com.zjh.tienchin.course.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tienchin/business")
public class BusinessController extends BaseController {

    @Autowired
    IBusinessService businessService;

    @Autowired
    IChannelService channelService;

    @Autowired
    IActivityService activityService;

    @Autowired
    ICourseService courseService;

    @PreAuthorize("@ss.hasAnyPermi('tienchin:business:follow,tienchin:business:view')")
    @GetMapping("/course/{type}")
    public AjaxResult getCourseByCourseType(@PathVariable Integer type) {
        return courseService.getCourseByCourseType(type);
    }

    @PreAuthorize("@ss.hasAnyPermi('tienchin:business:follow,tienchin:business:view')")
    @GetMapping("/all_course")
    public AjaxResult getAllCourse() {
        return AjaxResult.success(courseService.list());
    }

    @PreAuthorize("@ss.hasAnyPermi('tienchin:business:follow,tienchin:business:view')")
    @GetMapping("/{id}")
    public AjaxResult getBusinessById(@PathVariable Integer id) {
        return businessService.getBusinessById(id);
    }

    @PreAuthorize("@ss.hasPermi('tienchin:clue:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusinessVO businessVO) {
        startPage();
        List<BusinessSummary> list = businessService.selectBusinessList(businessVO);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('tienchin:business:follow')")
    @PostMapping("/follow")
    public AjaxResult follow(@RequestBody @Validated BusinessFollow businessFollow) {
        return businessService.follow(businessFollow);
    }

    @PreAuthorize("@ss.hasPermi('tienchin:clue:create')")
    @Log(title = "商机管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Business business) {
        return businessService.addBusiness(business);
    }

    @PreAuthorize("@ss.hasPermi('tienchin:business:create')")
    @GetMapping("/channels")
    public AjaxResult getAllChannels() {
        return AjaxResult.success(channelService.list());
    }

    @PreAuthorize("@ss.hasPermi('tienchin:business:create')")
    @GetMapping("/activity/{channelId}")
    public AjaxResult getActivityByChannelId(@PathVariable Integer channelId) {
        return activityService.selectActivityByChannelId(channelId);
    }

    /**
     * 根据商机的 ID 查询一个商机的摘要信息
     * @param businessId
     * @return
     */
    @GetMapping("/summary/{businessId}")
    @PreAuthorize("hasPermission('tienchin:business:edit')")
    public AjaxResult getBusinessSummaryByBusinessId(@PathVariable Integer businessId) {
        return businessService.getBusinessSummaryByBusinessId(businessId);
    }


    @PreAuthorize("hasPermission('tienchin:business:edit')")
    @PutMapping
    public AjaxResult updateBusiness(@RequestBody BusinessSummaryEnhance businessSummaryEnhance) {
        return businessService.updateBusiness(businessSummaryEnhance);
    }

    @PreAuthorize("hasPermission('tienchin:business:remove')")
    @DeleteMapping("/{businessIds}")
    public AjaxResult deleteBusinessById(@PathVariable Integer[] businessIds) {
        return businessService.deleteBusinessById(businessIds);
    }

}
