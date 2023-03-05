package com.zjh.tienchin.web.controller.tienchin;

import com.zjh.tienchin.channel.domain.Channel;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-13
 */
@RestController
@RequestMapping("/tienchin/channel")
public class ChannelController extends BaseController {
    @Autowired
    IChannelService channelService;

    @PreAuthorize("@ss.hasPermi('tienchin:channel:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChannelVO channelVO) {
        startPage();
        List<Channel> list = channelService.selectChannelList(channelVO);
        return getDataTable(list);
    }

    /**
     * 添加渠道
     */
    @PreAuthorize("@ss.hasPermi('tienchin:channel:create')")
    @Log(title = "渠道管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ChannelVO channelVO) {
        return channelService.addChannel(channelVO);
    }

    /**
     * 修改渠道
     */
    @PreAuthorize("@ss.hasPermi('tienchin:channel:edit')")
    @Log(title = "渠道管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ChannelVO channelVO) {

        return channelService.updateChannel(channelVO);
    }

    /**
     * 根据id获取详细信息
     * @param channelId
     * @return
     */
    @GetMapping("/{channelId}")
    @PreAuthorize("@ss.hasPermi('tienchin:channel:list')")
    public AjaxResult getInfo(@PathVariable Long channelId){
        return AjaxResult.success(channelService.getById(channelId));
    }

    /**
     * 删除渠道
     */
    @PreAuthorize("@ss.hasPermi('tienchin:channel:remove')")
    @Log(title = "渠道管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{channelIds}")
    public AjaxResult remove(@PathVariable Long[] channelIds) {

        return toAjax(channelService.deleteChannelByIds(channelIds));
    }

    @Log(title = "渠道管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('tienchin:channel:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Channel> util = new ExcelUtil<Channel>(Channel.class);
        List<Channel> channelList = util.importExcel(file.getInputStream());
        return AjaxResult.success(channelService.importChannel(channelList,updateSupport));
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Channel> util = new ExcelUtil<Channel>(Channel.class);
        util.importTemplateExcel(response, "渠道数据");
    }


    /**
     * 导出渠道
     * @param response
     * @param channelVo
     */
    @Log(title = "渠道管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('tienchin:channel:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChannelVO channelVo) {
        List<Channel> list = channelService.selectChannelList(channelVo);
        ExcelUtil<Channel> util = new ExcelUtil<Channel>(Channel.class);
        util.exportExcel(response, list, "渠道数据");
    }
}
