package com.iresearch.service;

import com.iresearch.entity.College;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iresearch.entity.Subject;
import com.iresearch.vo.CollegeVO;
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
public interface ICollegeService extends IService<College> {
    /**
     * college2CollegeVO
     */
    CollegeVO college2CollegeVO(College college);
    /**
     * college2CollegeVO
     */
    List<CollegeVO> collegeList2CollegeVOList(List<College> colleges);
}
