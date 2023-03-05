package com.zjh.tienchin.course.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjh.tienchin.common.annotation.Excel;
import com.zjh.tienchin.common.validator.CreateGroup;
import com.zjh.tienchin.common.validator.EditGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-16
 */
@TableName("tienchin_course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "course_id", type = IdType.AUTO)
    @NotNull(message = "课程ID不能为空",groups = {EditGroup.class})
    @Excel(name = "课程编号")
    private Integer courseId;

    /**
     * 课程类型 1 舞蹈类 2 游泳类 3 拳击类
     */
    @NotNull(message = "课程类型不能为空",groups = {CreateGroup.class,EditGroup.class})
    @Excel(name = "课程类型", readConverterExp = "1=舞蹈类,2=游泳类,3=拳击类")
    private Integer type;

    /**
     * 课程名
     */
    @NotBlank(message = "课程名称不能为空",groups = {CreateGroup.class,EditGroup.class})
    @Excel(name = "课程名称")
    private String name;

    /**
     * 课程价格
     */
    @NotNull(message = "课程价格不能为空",groups = {CreateGroup.class,EditGroup.class})
    @Min(value = 0, message = "非法的课程价格",groups = {CreateGroup.class,EditGroup.class})
    @Excel(name = "课程价格")
    private Double price;


    /**
     * 课程适用人群
     */
    @NotNull(message = "课程适用人群不能为空",groups = {CreateGroup.class,EditGroup.class})
    @Excel(name = "适用人群", readConverterExp = "1=中小学生,2=上班族,3=小白用户,4=健身达人")
    private Integer applyTo;

    /**
     * 课程简介
     */
    @NotBlank(message = "课程简介不能为空",groups = {CreateGroup.class,EditGroup.class})
    @Excel(name = "课程简介")
    private String info;

    private String remark;

    @Excel(name = "创建时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private String updateBy;

    private Integer delFlag;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getApplyTo() {
        return applyTo;
    }

    public void setApplyTo(Integer applyTo) {
        this.applyTo = applyTo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Course{" +
            "courseId = " + courseId +
            ", type = " + type +
            ", name = " + name +
            ", price = " + price +
            ", applyTo = " + applyTo +
            ", info = " + info +
            ", remark = " + remark +
            ", createTime = " + createTime +
            ", createBy = " + createBy +
            ", updateTime = " + updateTime +
            ", updateBy = " + updateBy +
            ", delFlag = " + delFlag +
        "}";
    }
}
