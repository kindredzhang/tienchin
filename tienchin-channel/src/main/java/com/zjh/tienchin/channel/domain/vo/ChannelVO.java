package com.zjh.tienchin.channel.domain.vo;
import com.zjh.tienchin.common.core.domain.BaseEntity;
import javax.validation.constraints.*;

public class ChannelVO extends BaseEntity {

    private Integer channelId;

    /**
     * 渠道名称
     */
    @NotBlank(message = "渠道名称不能为空")
    private String channelName;

    /**
     * 渠道状态
     */
    @Max(value = 1,message = "渠道状态值非法")
    @Min(value = 0,message = "渠道状态值非法")
    @NotNull(message = "渠道状态值必填")
    private Byte status;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 渠道类型：1 线上渠道 2线下渠道
     */
    @Max(value = 2,message = "渠道类型值非法")
    @Min(value = 1,message = "渠道类型值非法")
    @NotNull(message = "渠道类型值必填")
    private Integer type;


    private Integer delFlag;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
