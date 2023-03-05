package com.zjh.tienchin.web.controller.tienchin;

import com.zjh.tienchin.activity.service.IActivityService;
import com.zjh.tienchin.activity.domain.vo.ActivityVO;
import com.zjh.tienchin.common.validator.CreateGroup;
import com.zjh.tienchin.common.validator.EditGroup;
import com.zjh.tienchin.channel.domain.vo.ChannelVO;
import com.zjh.tienchin.channel.service.IChannelService;
import com.zjh.tienchin.common.annotation.Log;
import com.zjh.tienchin.common.core.controller.BaseController;
import com.zjh.tienchin.common.core.domain.AjaxResult;
import com.zjh.tienchin.common.core.page.TableDataInfo;
import com.zjh.tienchin.common.enums.BusinessType;
import com.zjh.tienchin.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-15
 */
@RestController
@RequestMapping("/tienchin/activity")
public class ActivityController extends BaseController {
    @Autowired
    IActivityService activityService;
    @Autowired
    IChannelService channelService;

    //@PreAuthorize("@ss.hasPermi('tienchin:channel:list')")
    @GetMapping("/channel/list")
    public AjaxResult channelList() {
        return AjaxResult.success(channelService.selectChannelList(new ChannelVO()));
    }

    @PreAuthorize("@ss.hasPermi('tienchin:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityVO activityVO) {
        startPage();
        List<ActivityVO> list = activityService.selectActivityList(activityVO);
        return getDataTable(list);
    }


    /**
     * 添加活动
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:create')")
    @Log(title = "活动管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody ActivityVO activityVO) {
        return activityService.addActivity(activityVO);
    }

    /**
     * 修改活动
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:edit')")
    @Log(title = "活动管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody ActivityVO activityVO) {

        return activityService.updateActivity(activityVO);
    }

    /**
     * 根据id获取详细信息
     * @param activityId
     * @return
     */
    @GetMapping("/{activityId}")
    @PreAuthorize("@ss.hasPermi('tienchin:activity:edit')")
    public AjaxResult getInfo(@PathVariable Long activityId){
        return AjaxResult.success(activityService.getActivityById(activityId));
    }

    /**
     * 删除活动
     */
    @PreAuthorize("@ss.hasPermi('tienchin:activity:remove')")
    @Log(title = "活动管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{activityIds}")
    public AjaxResult remove(@PathVariable Long[] activityIds) {

        return toAjax(activityService.deleteActivityByIds(activityIds));
    }


    /**
     * 导出渠道
     * @param response
     * @param activityVO
     */
    @Log(title = "活动管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('tienchin:activity:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityVO activityVO) {
        List<ActivityVO> list = activityService.selectActivityList(activityVO);
        ExcelUtil<ActivityVO> util = new ExcelUtil<>(ActivityVO.class);
        util.exportExcel(response, list, "活动数据");
    }
}
