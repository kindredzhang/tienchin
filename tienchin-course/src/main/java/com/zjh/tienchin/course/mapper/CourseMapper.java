package com.zjh.tienchin.course.mapper;

import com.zjh.tienchin.course.domain.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjh.tienchin.course.domain.vo.CourseVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-16
 */
public interface CourseMapper extends BaseMapper<Course> {

    List<Course> selectCourseList(CourseVO courseVO);
}
