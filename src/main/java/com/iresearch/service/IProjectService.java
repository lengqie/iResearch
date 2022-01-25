package com.iresearch.service;

import com.iresearch.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iresearch.vo.ProjectVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lengqie
 * @since 2022-01-20
 */
public interface IProjectService extends IService<Project> {

    /**
     * 修改用户状态
     * @return
     */
    boolean updateProjectStatus(Integer id,Integer status);

    /**
     * 判断用户是否具有项目所有权
     */
    boolean isOwnByUserName(Integer id, String name);

    /**
     * project 转 ProjectVo
     */
    ProjectVO project2ProjectVO(Project project);

    /**
     * projectList 转 ProjectVoList
     */
    List<ProjectVO> projectList2ProjectVOList(List<Project> projects);

}
