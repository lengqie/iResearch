package com.iresearch.service;

import com.iresearch.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iresearch.vo.SubjectVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lengqie
 * @since 2022-01-20
 */
public interface ISubjectService extends IService<Subject> {
    /**
     * subject2SubjectVO
     */
    SubjectVO subject2SubjectVO(Subject subject);

    /**
     * subjectList2SubjectVOList
     */
    List<SubjectVO> subjectList2SubjectVOList(List<Subject> subjects);
}
