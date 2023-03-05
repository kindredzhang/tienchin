package com.zjh.tienchin.web.controller.tienchin.clue;

import com.zjh.tienchin.assignment.domain.Assignment;
import com.zjh.tienchin.assignment.service.IAssignmentService;
import com.zjh.tienchin.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kindredzhang
 * @since 2023-02-17
 */
@RestController
@RequestMapping("/tienchin/assignment")
public class AssignmentController {
    @Autowired
    IAssignmentService assignmentService;
    /**
     * 分配线索
     */
    @PreAuthorize("@ss.hasAnyPermi('tienchin:clue:assignment,tienchin:business:assignment')")
    @PostMapping
    public AjaxResult assignClue(@Validated @RequestBody Assignment assignment) {
        return assignmentService.assignClue(assignment);
    }

}
