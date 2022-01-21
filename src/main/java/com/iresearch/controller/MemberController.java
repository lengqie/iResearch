package com.iresearch.controller;


import com.iresearch.entity.Member;
import com.iresearch.service.IMemberService;
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
@RequestMapping("/iresearch/member")
public class MemberController {
    @Autowired
    IMemberService iMemberService;

    @GetMapping
    public List<Member> getMembers(){
        final List<Member> members = iMemberService.list();
        return members;
    }
}

