package com.iresearch.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iresearch.constant.ProjectEnum;
import com.iresearch.constant.RoleEnum;
import com.iresearch.entity.Project;
import com.iresearch.entity.User;
import com.iresearch.service.IProjectService;
import com.iresearch.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
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
@RequestMapping("/iresearch/project")
public class ProjectController {

    @Autowired
    IProjectService iProjectService;

    @Autowired
    IUserService iUserService;

    /**
     * 获取全部项目
     * @param response response
     */
    @RequiresRoles({"user","admin"})
    @GetMapping
    public List<Project> getProjects(HttpServletResponse response){
        // 权限判断
        final String username = (String) SecurityUtils.getSubject().getPrincipal();
        final String userTypeString = iUserService.getUserTypeStringByName(username);

        final List<Project> projects;
        // 用户只能获取自己的 项目
        if (RoleEnum.USER.value().equals(userTypeString)){
            final User user = iUserService.getUserByName(username);
            LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Project::getCreateId,user.getId());
            projects = iProjectService.list(wrapper);

        } else {
            projects = iProjectService.list();
        }
        if(projects == null || projects.size() == 0){
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            response.setStatus(HttpStatus.OK.value());
            return projects;
        }
        return null;
    }

    /**
     * 通过Id 获取项目
     * @param  id ProjectId
     * @return Project
     */
    @RequiresRoles({"user","admin"})
    @GetMapping("/{id}")
    public Project getProjectById( @PathVariable String id,HttpServletResponse response){
        final Project project = iProjectService.getById(id);
        if (project == null){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        // 判断权限
        final String username = (String) SecurityUtils.getSubject().getPrincipal();
        final User user = iUserService.getUserByName(username);
        final String typeString = iUserService.getUserTypeStringByName(username);
        // 当前角色是用户 且 项目创建者不是 当前用户
        if (RoleEnum.USER.value().equals(typeString) && project.getCreateId().equals(user.getId())){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }
        response.setStatus(HttpStatus.OK.value());
        return project;
    }

    /**
     * 添加项目
     */
    @RequiresRoles({"user","admin"})
    @PostMapping
    public void addProject(String name, int college, int subject,String inCharge, int type,
                           String projectPurpose,String economicAnalysis, String existingConditions,
                           String expectedResult, String viableAnalysis,
                           HttpServletResponse response){

        Project project = new Project();
        projectSetter(name, college, subject, inCharge, type, projectPurpose, economicAnalysis, existingConditions, expectedResult, viableAnalysis,
                project);
        // 获取当前用户id
        final String username = (String) SecurityUtils.getSubject().getPrincipal();
        final User user = iUserService.getUserByName(username);
        final int userId = user.getId();

        project.setCreateId(userId);
        project.setCreateTime(LocalDateTime.now());
        project.setProjectStatus(0);
        // 保存到数据库
        final boolean save = iProjectService.save(project);
        if (save){
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    /**
     * 修改项目
     */
    @RequiresRoles({"user","admin"})
    @PutMapping("/{id}")
    public void updateProjectById(@PathVariable int id, String name, int college, int subject,String inCharge, int type,
                              String projectPurpose,String economicAnalysis, String existingConditions,
                                  String expectedResult, String viableAnalysis,
                              HttpServletResponse response) {
        // 查找项目
        final Project project = iProjectService.getById(id);
        if (project == null){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        // 判断权限
        final String username = (String) SecurityUtils.getSubject().getPrincipal();
        final String typeString = iUserService.getUserTypeStringByName(username);
        final boolean own = iProjectService.isOwnByUserName(id, username);
        // 当前角色是用户 且 不是 项目创建者 无权修改
        if (RoleEnum.USER.value().equals(typeString) && !own){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        // 修改项目
        projectSetter(name, college, subject, inCharge, type, projectPurpose, economicAnalysis, existingConditions, expectedResult, viableAnalysis,
                project);
        project.setUpdateTime(LocalDateTime.now());
        // 保存数据库
        final boolean save = iProjectService.updateById(project);
        if (save) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    /**
     * 修改用户状态 管理员
     * @param id
     * @param status
     */
    @PutMapping("/{id}/status/{status}")
    @RequiresRoles({"admin"})
    public void changeStatus(@PathVariable Integer id,@PathVariable Integer status,
                              HttpServletResponse response){

        // 查找项目
        final Project project = iProjectService.getById(id);
        if (project == null){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        // 判断权限
        final String username = (String) SecurityUtils.getSubject().getPrincipal();
        final String typeString = iUserService.getUserTypeStringByName(username);
        final boolean own = iProjectService.isOwnByUserName(id, username);
        // 当前角色是 用户 且 不是 创建者 无权修改
        if (RoleEnum.USER.value().equals(typeString) && !own){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        // 当前角色是 用户 且 是 创造者
        else if (RoleEnum.USER.value().equals(typeString)){
            // 提交合法性判断
            // 只有两种情况可以 能够更改
            final Integer projectStatus = project.getProjectStatus();
            // 1. project 状态为 created 修改 为 applying
            if (ProjectEnum.CREATED.value().equals(projectStatus) && ProjectEnum.APPLYING.value().equals(status)){
            }
            // 2. project 状态为 apply 修改 为 ending
            else if (ProjectEnum.APPLY.value().equals(projectStatus) && ProjectEnum.ENDING.value().equals(status)){
            }
            else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                return;
            }
        }
        // 当前角色是 管理员 或 用户 合法时
        final boolean b = iProjectService.updateProjectStatus(id, status);
        if (b){
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    // 简化代码
    private void projectSetter(String name, int college, int subject, String inCharge, int type, String projectPurpose, String economicAnalysis, String existingConditions, String expectedResult, String viableAnalysis, @NotNull Project project) {
        project.setName(name);
        project.setCollegeId(college);
        project.setSubjectId(subject);
        project.setInCharge(inCharge);
        project.setProjectType(type);
        project.setProjectPurpose(projectPurpose);
        project.setEconomicAnalysis(economicAnalysis);
        project.setExistingConditions(existingConditions);
        project.setExpectedResult(expectedResult);
        project.setViableAnalysis(viableAnalysis);
    }
}
