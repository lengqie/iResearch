package com.iresearch.controller;

import com.iresearch.entity.User;
import com.iresearch.service.IUserService;
import com.iresearch.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lengqie
 */
@RestController
public class CommonController {

    @Autowired
    IUserService iUserService;

    @GetMapping("/login")
    public UserVO login(String name, String password,
                        HttpServletResponse response){
        // 输入处理
        if (name == null || password == null){
            response.setStatus(HttpStatus.OK.value());
            return null;
        }
        final Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(name,password));
        final String username = ((String) subject.getPrincipal());
        final User user = iUserService.getUserByName(username);
        return iUserService.user2userVO(user);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response){
        final Subject subject = SecurityUtils.getSubject();
        subject.logout();
        response.setStatus(HttpStatus.OK.value());
    }

    /**
     * 没有权限
     * @param response HttpServletResponse
     */
    @GetMapping("/401")
    public void unauthorizedUrl(HttpServletResponse response){
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
    /**
     * 没有权限
     */
    @GetMapping("/id/{id}")
    public void test(@PathVariable int id){
        System.out.println(id);
    }
}
