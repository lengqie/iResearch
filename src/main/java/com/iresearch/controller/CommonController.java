package com.iresearch.controller;

import com.iresearch.entity.User;
import com.iresearch.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lengqie
 */
@RestController
public class CommonController {

    @Autowired
    IUserService userService;

    @GetMapping("/login")
    public User login(String name, String password){

        // 输入处理

        final Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(name,password));
        return new User();
    }
}
