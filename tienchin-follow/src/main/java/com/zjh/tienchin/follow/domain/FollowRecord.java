package com.zjh.tienchin.follow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author kindredzhang
 * @since 2023-03-04
 */
@TableName("tienchin_follow_record")
public class FollowRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    /**
     * 跟进记录类型 1 线索 2 商机
     */
    private Integer type;

    /**
     * 线索或者商机的id
     */
    private Integer assignId;

    private String info;

    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private LocalDateTime updateBy;

    private String remark;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAssignId() {
        return assignId;
    }

    public void setAssignId(Integer assignId) {
        this.assignId = assignId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(LocalDateTime updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "FollowRecord{" +
            "recordId = " + recordId +
            ", type = " + type +
            ", assignId = " + assignId +
            ", info = " + info +
            ", createTime = " + createTime +
            ", createBy = " + createBy +
            ", updateTime = " + updateTime +
            ", updateBy = " + updateBy +
            ", remark = " + remark +
        "}";
    }
}
