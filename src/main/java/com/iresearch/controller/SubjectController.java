package com.iresearch.controller;


import com.iresearch.entity.Subject;
import com.iresearch.service.ISubjectService;
import com.iresearch.vo.SubjectVO;
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
@RequestMapping("/iresearch/subject")
public class SubjectController {
    @Autowired
    ISubjectService iSubjectService;

    /**
     *  获取全部suject
     * @return
     */
    @GetMapping
    public List<SubjectVO> getSubjects(){
        final List<Subject> subjects = iSubjectService.list();
        return iSubjectService.subjectList2SubjectVOList(subjects);
    }

    /**
     * 根据id获取 subject
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    public SubjectVO getSubject(@PathVariable String id,
                                HttpServletResponse response){
        final Subject service = iSubjectService.getById(id);
        if (service == null){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        response.setStatus(HttpStatus.OK.value());
        return iSubjectService.subject2SubjectVO(service);
    }
}

