package com.iresearch.controller;


import com.iresearch.entity.College;
import com.iresearch.service.ICollegeService;
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
@RequestMapping("/iresearch/college")
public class CollegeController {

    @Autowired
    ICollegeService iCollegeService;

    @GetMapping()
    public List<College> getColleges(){
        final List<College> colleges = iCollegeService.list();
        return colleges;
    }
}

