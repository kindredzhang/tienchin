package com.zjh.tienchin.activity.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjh.tienchin.common.validator.CreateGroup;
import com.zjh.tienchin.common.validator.EditGroup;
import com.zjh.tienchin.common.annotation.Excel;
import com.zjh.tienchin.common.core.domain.BaseEntity;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class ActivityVO extends BaseEntity {


    /**
     * 活动ID
     */
    @NotNull(message = "活动ID不能为空" ,groups = EditGroup.class)
    @Excel(name = "活动ID")
    private Integer activityId;

    /**
     * 活动名称
     */
    @NotBlank(message = "活动名称不能为空" ,groups = {CreateGroup.class, EditGroup.class})
    @Size(min = 0,max = 20,message = "活动名称最大为20个字符")
    @Excel(name = "活动名称")
    private String name;

    /**
     * 渠道ID
     */
    @NotNull(message = "渠道ID不能为空",groups = {CreateGroup.class, EditGroup.class})
    @Excel(name = "渠道ID")
    private Integer channelId;
    @Excel(name = "渠道名称")
    private String channelName;

    /**
     * 活动简介
     */
    @NotBlank(message = "活动简介不能为空",groups = {CreateGroup.class, EditGroup.class})
    @Size(min = 0, max = 255, message = "活动简介最多255字")
    @Excel(name = "活动简介")
    private String info;


    /**
     * 活动类型
     */
    @NotNull(message = "活动类型值必填",groups = {CreateGroup.class, EditGroup.class})
    @Excel(name = "活动类型", readConverterExp = "1=折扣券,2=代金券")
    private Integer type;

    /**
     * 折扣券
     */
    @Min(value = 0,message = "折扣券值无效",groups = {CreateGroup.class, EditGroup.class})
    @Max(value = 10,message = "折扣券值无效",groups = {CreateGroup.class, EditGroup.class})
    @Excel(name = "活动折扣")
    private Double discount;

    /**
     * 代金券
     */
    @Min(value = 0,message = "代金券值无效",groups = {CreateGroup.class, EditGroup.class})
    @Excel(name = "代金券面值")
    private Double voucher;

    /**
     * 活动状态 0 禁用 1 启动
     */
    @Max(value = 1,message = "活动状态值非法",groups = {CreateGroup.class, EditGroup.class})
    @Min(value = 0,message = "活动状态值非法",groups = {CreateGroup.class, EditGroup.class})
    @Excel(name = "活动状态", readConverterExp = "0=过期,1=正常")
    private Integer status;

    @NotNull(message = "活动开始时间不能为空",groups = {CreateGroup.class, EditGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @Excel(name = "活动开始时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    @NotNull(message = "活动结束时间不能为空",groups = {CreateGroup.class, EditGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @Excel(name = "活动结束时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * 活动备注
     */
    @Excel(name = "活动备注")
    private String remark;

    private Integer delFlag;

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getVoucher() {
        return voucher;
    }

    public void setVoucher(Double voucher) {
        this.voucher = voucher;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
