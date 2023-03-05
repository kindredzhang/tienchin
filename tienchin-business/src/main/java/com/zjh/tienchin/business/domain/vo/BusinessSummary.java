package com.zjh.tienchin.business.domain.vo;

import com.zjh.tienchin.common.core.domain.BaseEntity;

import java.time.LocalDateTime;

/**
 * 商机摘要信息
 */
public class BusinessSummary extends BaseEntity {
    private Integer businessId;
    private String name;
    private String phone;
    private String owner;
    private Integer status;
    private LocalDateTime nextTime;

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getNextTime() {
        return nextTime;
    }

    public void setNextTime(LocalDateTime nextTime) {
        this.nextTime = nextTime;
    }
}
