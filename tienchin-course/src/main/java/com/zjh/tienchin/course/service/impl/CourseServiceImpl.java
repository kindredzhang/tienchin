package com.zjh.tienchin.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjh.tienchin.common.core.domain.AjaxResult;
import com.zjh.tienchin.common.utils.SecurityUtils;
import com.zjh.tienchin.course.domain.Course;
import com.zjh.tienchin.course.domain.vo.CourseVO;
import com.zjh.tienchin.course.mapper.CourseMapper;
import com.zjh.tienchin.course.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-16
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    CourseMapper courseMapper;

    @Override
    public List<Course> selectCourseList(CourseVO courseVO) {
        return courseMapper.selectCourseList(courseVO);

    }

    @Override
    public AjaxResult addCourse(Course course) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Course::getName,course.getName()).eq(Course::getDelFlag,0);
        Course c = getOne(wrapper);
        if (c != null) {
            //说明存在一个同名的课程并且没有删除
            return AjaxResult.error("存在同名课程，添加失败");
        }
        course.setCreateBy(SecurityUtils.getUsername());
        course.setCreateTime(LocalDateTime.now());
        course.setDelFlag(0);
        return save(course)?AjaxResult.success("添加成功"): AjaxResult.error("添加失败");
    }

    @Override
    public AjaxResult updateCourse(Course course) {
        course.setCreateBy(null);
        course.setCreateTime(null);
        course.setDelFlag(null);
        course.setUpdateBy(SecurityUtils.getUsername());
        course.setUpdateTime(LocalDateTime.now());
        return updateById(course) ? AjaxResult.success("更新成功") : AjaxResult.error("更新失败");
    }

    @Override
    public Boolean deleteCourseByIds(Long[] courseIds) {
        UpdateWrapper<Course> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(Course::getDelFlag,1).in(Course::getCourseId,courseIds);
        return update(wrapper);
    }

    @Override
    public AjaxResult getCourseByCourseType(Integer type) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Course:: getType,type);
        List<Course> list = list(wrapper);
        return AjaxResult.success(list);
    }
}
