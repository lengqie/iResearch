package com.iresearch.service.impl;

import com.iresearch.entity.College;
import com.iresearch.mapper.CollegeMapper;
import com.iresearch.service.ICollegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iresearch.vo.CollegeVO;
import org.springframework.stereotype.Service;

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
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements ICollegeService {

    @Override
    public CollegeVO college2CollegeVO(College college) {
        CollegeVO collegeVO = new CollegeVO();
        collegeVO.setId(college.getId());
        collegeVO.setName(college.getName());
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
