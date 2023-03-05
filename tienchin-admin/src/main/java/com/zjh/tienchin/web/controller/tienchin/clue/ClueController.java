package com.zjh.tienchin.web.controller.tienchin.clue;

import com.zjh.tienchin.activity.service.IActivityService;
import com.zjh.tienchin.channel.service.IChannelService;
import com.zjh.tienchin.clue.domain.Clue;
import com.zjh.tienchin.clue.domain.vo.ClueDetails;
import com.zjh.tienchin.clue.domain.vo.ClueSummary;
import com.zjh.tienchin.clue.domain.vo.ClueVo;
import com.zjh.tienchin.clue.service.IClueService;
import com.zjh.tienchin.common.annotation.Log;
import com.zjh.tienchin.common.core.controller.BaseController;
import com.zjh.tienchin.common.core.domain.AjaxResult;
import com.zjh.tienchin.common.core.page.TableDataInfo;
import com.zjh.tienchin.common.enums.BusinessType;
import com.zjh.tienchin.common.validator.CreateGroup;
import com.zjh.tienchin.common.validator.EditGroup;
import com.zjh.tienchin.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-17
 */
@RestController
@RequestMapping("/tienchin/clue")
public class ClueController extends BaseController {

    @Autowired
    IClueService clueService;
    @Autowired
    IChannelService channelService;
    @Autowired
    IActivityService activityService;

    @Autowired
    ISysUserService sysUserService;

    //确认无效线索
    @PreAuthorize("@ss.hasPermi('tienchin:clue:follow')")

    @PostMapping("/invalid")
    public AjaxResult invalidClueFollow(@RequestBody ClueDetails clueDetails){
        return clueService.invalidClueFollow(clueDetails);
    }

    @PreAuthorize("@ss.hasPermi('tienchin:clue:follow')")
    @PostMapping("/follow")
    public AjaxResult clueFollow(@RequestBody ClueDetails clueDetails){
        return clueService.clueFollow(clueDetails);
    }

    /**
     * 线索转为商机
     * @param clueId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tienchin:business:assignment')")
    @PostMapping("/to_business/{clueId}")
    public AjaxResult clue2Business(@PathVariable Integer clueId){
        return clueService.clue2Business(clueId);
    }


    @GetMapping("/users/{deptId}")
    @PreAuthorize("@ss.hasPermi('tienchin:clue:assignment')")
    public AjaxResult getUsersByDeptId(@PathVariable Long deptId) {
        return sysUserService.getUsersByDeptId(deptId);
    }

    @PreAuthorize("@ss.hasPermi('tienchin:clue:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClueVo clueVO) {
        startPage();
        List<ClueSummary> list = clueService.selectClueList(clueVO);
        return getDataTable(list);
    }

    /**
     * 添加线索
     */
    @PreAuthorize("@ss.hasPermi('tienchin:clue:create')")
    @Log(title = "线索管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody Clue clue) {
        return clueService.addClue(clue);
    }

    @PreAuthorize("@ss.hasPermi('tienchin:clue:create')")
    @GetMapping("/channels")
    public AjaxResult getAllChannels() {
        return channelService.getAllChannels();
    }

    @PreAuthorize("@ss.hasPermi('tienchin:clue:create')")
    @GetMapping("/activities/{channelId}")
    public AjaxResult getAllActivities(@PathVariable("channelId") Integer channelId) {
        if (channelId == null) {
            return AjaxResult.error("渠道来源不能为空");
        }
        return activityService.selectActivityByChannelId(channelId);
    }

    /**
     * 根据id获取详情信息--用于跟进
     * 
     * @param clueId
     * @return
     */
    @GetMapping("/{clueId}")
    @PreAuthorize("@ss.hasAnyPermi('tienchin:clue:view,tiwnchin:clue:follow')")
    public AjaxResult getClueDetailsByClueId(@PathVariable Long clueId) {
        return clueService.getClueDetailsByClueId(clueId);
    }

    /**
     * 根据id获取简要信息--用于修改
     *
     * @param clueId
     * @return
     */
    @GetMapping("/summary/{clueId}")
    @PreAuthorize("@ss.hasPermi('tienchin:clue:update')")
    public AjaxResult getClueSummaryByClueId(@PathVariable Long clueId) {
        return clueService.getClueSummaryByClueId(clueId);
    }


    /**
     * 修改线索
     */
    @PreAuthorize("@ss.hasPermi('tienchin:clue:edit')")
    @Log(title = "线索管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateClue(@Validated(EditGroup.class)@RequestBody Clue clue) {

        return clueService.updateClue(clue);
    }
}
