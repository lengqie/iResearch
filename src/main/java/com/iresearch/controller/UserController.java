package com.iresearch.controller;


import com.iresearch.entity.User;
import com.iresearch.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
@RequestMapping("/iresearch/user")
public class UserController {

    @Autowired
    IUserService iUserService;

    @GetMapping
    public List<User> getUsers(){
        final List<User> users = iUserService.list();
        return users;
    }

    @PostMapping
    public void addUser(String name, String password, String nickName, int type,
                        HttpServletResponse response){
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setNickname(nickName);
        user.setUserType(type);
        user.setCreateTime(LocalDateTime.now());

        final boolean save = iUserService.save(user);
        if (!save){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else{
            response.setStatus(HttpStatus.OK.value());
        }
    }

    @PutMapping
    public void updateUser(int id, String name, String password, String nickName, int type,
                        HttpServletResponse response){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setNickname(nickName);
        user.setUserType(type);
        user.setUpdateTime(LocalDateTime.now());

        final boolean save = iUserService.updateById(user);
        if (!save){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else{
            response.setStatus(HttpStatus.OK.value());
        }
    }

}

