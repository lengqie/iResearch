package com.iresearch.controller;


import com.iresearch.entity.College;
import com.iresearch.service.ICollegeService;
import com.iresearch.vo.CollegeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 获取全部 college
     * @return
     */
    @GetMapping()
    public List<CollegeVO> getColleges(){
        final List<College> collegeList = iCollegeService.list();
        final List<CollegeVO> collegeVOList = iCollegeService.collegeList2CollegeVOList(collegeList);
        return collegeVOList;
    }
    @GetMapping("/{id}")
    public CollegeVO getCollegeById(@PathVariable String id,
                                    HttpServletResponse response){
        final College college = iCollegeService.getById(id);
        if (college == null){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        response.setStatus(HttpStatus.OK.value());
        return iCollegeService.college2CollegeVO(college);
    }


}

