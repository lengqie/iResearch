package com.iresearch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iresearch.entity.College;
import com.iresearch.entity.Subject;
import com.iresearch.mapper.CollegeMapper;
import com.iresearch.mapper.SubjectMapper;
import com.iresearch.service.ICollegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iresearch.vo.CollegeVO;
import com.iresearch.vo.SubjectVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
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
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements ICollegeService {

    @Resource
    SubjectMapper subjectMapper;

    @Resource
    CollegeMapper collegeMapper;


    @Override
    public CollegeVO college2CollegeVO(College college) {
        CollegeVO collegeVO = new CollegeVO();
        collegeVO.setId(college.getId());
        collegeVO.setName(college.getName());

        LambdaQueryWrapper<Subject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subject::getCollegeId,college.getId());
        final List<Subject> subjects = subjectMapper.selectList(wrapper);

        List<SubjectVO> subjectVOList = new LinkedList<>();
        for (Subject subject : subjects) {
            SubjectVO subjectVO = new SubjectVO();
            subjectVO.setId(subject.getId());
            subjectVO.setName(subject.getName());
            subjectVOList.add(subjectVO);
        }
        collegeVO.setSubjects(subjectVOList);
        return collegeVO;
    }

    @Override
    public List<CollegeVO> collegeList2CollegeVOList(List<College> colleges) {
        List<CollegeVO> collegeVOList = new ArrayList<>();
        for (College college : colleges) {
            collegeVOList.add(college2CollegeVO(college));
        }
        return collegeVOList;
    }
}
