package com.iresearch.controller;


import com.iresearch.entity.Funds;
import com.iresearch.service.IFundsService;
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
@RequestMapping("/iresearch/funds")
public class FundsController {
    @Autowired
    IFundsService iFundsService;

    @GetMapping
    public List<Funds> getFunds(){
        final List<Funds> funds = iFundsService.list();
        return funds;
    }
}

