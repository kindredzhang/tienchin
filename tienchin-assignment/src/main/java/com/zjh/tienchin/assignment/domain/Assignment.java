package com.zjh.tienchin.assignment.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-17
 */
@TableName("tienchin_assignment")
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分配的id
     */
    @TableId(value = "aid", type = IdType.AUTO)
    private Integer aid;

    /**
     * 1 线索 2 商机
     */
    private Integer type;


    /**
     * 线索或者商机的 ID
     */
    @NotNull(message = "线索或者商机ID不能为空")
    private Integer assignId;

    /**
     * 线索所属的用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    /**
     * 线索所属的用户名
     */
    @NotNull(message = "用户名不能为空")
    private String userName;

    /**
     * 线索所属用户的部门ID
     */
    @NotNull(message = "部门Id不能为空")
    private Long deptId;


    /**
     * 是否是当前最新分配人
     */
    private Boolean latest;

    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private String updateBy;

    private String remark;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Boolean getLatest() {
        return latest;
    }

    public void setLatest(Boolean latest) {
        this.latest = latest;
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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
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
        return "Assignment{" +
            "aid = " + aid +
            ", type = " + type +
            ", assignId = " + assignId +
            ", userId = " + userId +
            ", userName = " + userName +
            ", deptId = " + deptId +
            ", latest = " + latest +
            ", createTime = " + createTime +
            ", createBy = " + createBy +
            ", updateTime = " + updateTime +
            ", updateBy = " + updateBy +
            ", remark = " + remark +
        "}";
    }
}
