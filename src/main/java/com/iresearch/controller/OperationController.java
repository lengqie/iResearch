package com.iresearch.controller;


import com.iresearch.entity.Operation;
import com.iresearch.service.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lengqie
 * @since 2022-01-20
 */
@Controller
@RequestMapping("/iresearch/operation")
public class OperationController {
    @Autowired
    IOperationService iOperationService;

    @GetMapping
    public List<Operation> getOperations(){
        final List<Operation> operations = iOperationService.list();
        return operations;
    }
}

