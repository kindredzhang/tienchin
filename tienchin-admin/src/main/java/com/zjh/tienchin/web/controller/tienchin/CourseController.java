package com.zjh.tienchin.web.controller.tienchin;

import com.zjh.tienchin.common.utils.poi.ExcelUtil;
import com.zjh.tienchin.common.validator.CreateGroup;
import com.zjh.tienchin.common.annotation.Log;
import com.zjh.tienchin.common.core.controller.BaseController;
import com.zjh.tienchin.common.core.domain.AjaxResult;
import com.zjh.tienchin.common.core.page.TableDataInfo;
import com.zjh.tienchin.common.enums.BusinessType;
import com.zjh.tienchin.common.validator.EditGroup;
import com.zjh.tienchin.course.domain.Course;
import com.zjh.tienchin.course.domain.vo.CourseVO;
import com.zjh.tienchin.course.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-16
 */
@RestController
@RequestMapping("/tienchin/course")
public class CourseController extends BaseController {

    @Autowired
    ICourseService courseService;

    @PreAuthorize("@ss.hasPermi('tienchin:course:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseVO courseVO) {
        startPage();
        List<Course> list = courseService.selectCourseList(courseVO);
        return getDataTable(list);
    }


    /**
     * 添加课程
     */
    @PreAuthorize("@ss.hasPermi('tienchin:course:create')")
    @Log(title = "课程管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody Course course) {
        return courseService.addCourse(course);
    }

    /**
     * 修改课程
     */
    @PreAuthorize("@ss.hasPermi('tienchin:course:edit')")
    @Log(title = "课程管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated(EditGroup.class) @RequestBody Course course) {

        return courseService.updateCourse(course);
    }

    /**
     * 根据id获取详细信息
     * @param courseId
     * @return
     */
    @GetMapping("/{courseId}")
    @PreAuthorize("@ss.hasPermi('tienchin:course:edit')")
    public AjaxResult getInfo(@PathVariable Long courseId){
        return AjaxResult.success(courseService.getById(courseId));
    }

    /**
     * 删除课程
     */
    @PreAuthorize("@ss.hasPermi('tienchin:course:remove')")
    @Log(title = "课程管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{courseIds}")
    public AjaxResult remove(@PathVariable Long[] courseIds) {

        return toAjax(courseService.deleteCourseByIds(courseIds));
    }


    /**
     * 导出课程
     * @param response
     * @param courseVO
     */
    @Log(title = "课程管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('tienchin:course:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourseVO courseVO) {
        List<Course> list = courseService.selectCourseList(courseVO);
        ExcelUtil<Course> util = new ExcelUtil<>(Course.class);
        util.exportExcel(response, list, "课程数据");
    }
}
