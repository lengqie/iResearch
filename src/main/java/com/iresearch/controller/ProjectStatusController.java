package com.iresearch.controller;


import com.iresearch.entity.ProjectStatus;
import com.iresearch.service.IProjectStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lengqie
 * @since 2022-01-20
 */
@RestController
@RequestMapping("/iresearch/projectStatus")
public class ProjectStatusController {
    @Autowired
    IProjectStatusService iProjectStatusService;

    @GetMapping
    public List<ProjectStatus> getProjectStatuses(){
        final List<ProjectStatus> projectStatuses = iProjectStatusService.list();
        return projectStatuses;
    }
}

