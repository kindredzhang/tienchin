package com.zjh.tienchin.course.service;

import com.zjh.tienchin.common.core.domain.AjaxResult;
import com.zjh.tienchin.course.domain.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjh.tienchin.course.domain.vo.CourseVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-16
 */
public interface ICourseService extends IService<Course> {

    List<Course> selectCourseList(CourseVO courseVO);

    AjaxResult addCourse(Course course);

    AjaxResult updateCourse(Course course);

    Boolean deleteCourseByIds(Long[] courseIds);

    AjaxResult getCourseByCourseType(Integer type);
}
