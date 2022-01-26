package com.iresearch.service.impl;

import com.iresearch.entity.College;
import com.iresearch.entity.Subject;
import com.iresearch.mapper.CollegeMapper;
import com.iresearch.mapper.SubjectMapper;
import com.iresearch.service.ISubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iresearch.vo.CollegeVO;
import com.iresearch.vo.SubjectVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lengqie
 * @since 2022-01-20
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {

    @Resource
    SubjectMapper subjectMapper;

    @Resource
    CollegeMapper collegeMapper;

    @Override
    public SubjectVO subject2SubjectVO(Subject subject) {
        SubjectVO subjectVO = new SubjectVO();

        subjectVO.setId(subject.getId());
        subjectVO.setName(subject.getName());

        final Integer collegeId = subject.getCollegeId();
        final College college = collegeMapper.selectById(collegeId);
        CollegeVO collegeVO = new CollegeVO();
        collegeVO.setId(college.getId());
        collegeVO.setName(college.getName());

        subjectVO.setCollegeVO(collegeVO);
        return subjectVO;
    }

    @Override
    public List<SubjectVO> subjectList2SubjectVOList(List<Subject> subjects) {
        List<SubjectVO> subjectVOList = new ArrayList<>();
        for (Subject subject : subjects) {
            subjectVOList.add(subject2SubjectVO(subject));
        }
        return subjectVOList;
    }
}
