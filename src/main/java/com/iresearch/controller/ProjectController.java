package com.iresearch.controller;


import com.iresearch.entity.Project;
import com.iresearch.service.IProjectService;
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
@RequestMapping("/iresearch/project")
public class ProjectController {
    @Autowired
    IProjectService iProjectService;

    @GetMapping
    public List<Project> getProjects(){
        final List<Project> projects = iProjectService.list();
        return projects;
    }
}

