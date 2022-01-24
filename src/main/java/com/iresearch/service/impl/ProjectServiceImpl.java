package com.iresearch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iresearch.entity.Project;
import com.iresearch.entity.User;
import com.iresearch.mapper.ProjectMapper;
import com.iresearch.mapper.UserMapper;
import com.iresearch.service.IProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
