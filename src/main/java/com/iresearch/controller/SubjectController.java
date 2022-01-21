package com.iresearch.controller;


import com.iresearch.entity.Subject;
import com.iresearch.service.ISubjectService;
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
@RequestMapping("/iresearch/subject")
public class SubjectController {
    @Autowired
    ISubjectService iSubjectService;

    @GetMapping
    public List<Subject> getSubjects(){
        final List<Subject> subjects = iSubjectService.list();
        return subjects;
    }
}

