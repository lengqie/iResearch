package com.iresearch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iresearch.entity.*;
import com.iresearch.mapper.*;
import com.iresearch.service.IProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iresearch.vo.ProjectVO;
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
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    @Resource
    ProjectMapper projectMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    CollegeMapper collegeMapper;

    @Resource
    SubjectMapper subjectMapper;

    @Resource
    ProjectTypeMapper projectTypeMapper;

    @Resource
    ProjectStatusMapper projectStatusMapper;

    @Resource
    FileMapper fileMapper;

    @Override
    public boolean updateProjectStatus(Integer id, Integer status) {
        final Project project = projectMapper.selectById(id);
        project.setProjectStatus(status);
        final int i = projectMapper.updateById(project);
        return i==1;
    }

    @Override
    public boolean isOwnByUserName(Integer id, String name) {
        final Project project = projectMapper.selectById(id);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,name);
        final User user = userMapper.selectOne(wrapper);
        return project.getCreateId().equals(user.getId());
    }

    @Override
    public ProjectVO project2ProjectVO(Project project) {
        ProjectVO projectVO = new ProjectVO();

        projectVO.setId(project.getId());
        projectVO.setName(project.getName());

        if (project.getCollegeId()!=null){
            final Integer collegeId = project.getCollegeId();
            final College college = collegeMapper.selectById(collegeId);
            projectVO.setCollegeName(college.getName());
        }

        if (project.getSubjectId()!=null){
            final Integer subjectId = project.getSubjectId();
            final Subject subject = subjectMapper.selectById(subjectId);
            projectVO.setSubjectName(subject.getName());

        }
        if (project.getInCharge()!=null){
            projectVO.setInCharge(project.getInCharge());
        }

        if (project.getProjectType()!=null){
            final Integer projectType = project.getProjectType();
            final ProjectType type = projectTypeMapper.selectById(projectType);
            projectVO.setProjectTypeName(type.getType());
        }

        if (project.getFileId() != null){
            final Integer fileId = project.getFileId();
            final File file = fileMapper.selectById(fileId);
            projectVO.setFileName(file.getFile());
        }

        projectVO.setProjectPurpose(project.getProjectPurpose());
        projectVO.setEconomicAnalysis(project.getEconomicAnalysis());
        projectVO.setExistingConditions(project.getEconomicAnalysis());
        projectVO.setExpectedResult(project.getExpectedResult());
        projectVO.setViableAnalysis(project.getViableAnalysis());

        return projectVO;
    }

    @Override
    public List<ProjectVO> projectList2ProjectVOList(List<Project> projects) {
        List<ProjectVO> projectVOList = new ArrayList<>();
        for (Project project : projects) {
            projectVOList.add(project2ProjectVO(project));
        }
        return projectVOList;
    }
}
