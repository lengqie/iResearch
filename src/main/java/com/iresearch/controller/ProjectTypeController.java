package com.iresearch.controller;


import com.iresearch.entity.ProjectType;
import com.iresearch.service.IProjectTypeService;
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
@RequestMapping("/iresearch/projectType")
public class ProjectTypeController {

    @Autowired
    IProjectTypeService iProjectTypeService;;

    @GetMapping
    public List<ProjectType> getProjectTypes(){
        final List<ProjectType> projectTypes = iProjectTypeService.list();
        return projectTypes;
    }
}

