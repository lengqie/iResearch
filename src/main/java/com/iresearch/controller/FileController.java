package com.iresearch.controller;


import com.iresearch.entity.File;
import com.iresearch.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lengqie
 * @since 2022-01-20
 */
@RestController
@RequestMapping("/iresearch/file")
public class FileController {

    @Autowired
    IFileService iFileService;

    @GetMapping("/{id}")
    public File getFileByID(@PathVariable String id){
        final File file = iFileService.getById(id);
        return file;
    }
}

