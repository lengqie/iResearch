package com.iresearch.controller;


import com.iresearch.entity.UserType;
import com.iresearch.service.IUserTypeService;
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
@RequestMapping("/iresearch/userType")
public class UserTypeController {

    @Autowired
    IUserTypeService iUserTypeService;

    @GetMapping
    public List<UserType> getUserTypes(){
        final List<UserType> userTypes = iUserTypeService.list();
        return userTypes;
    }
}

