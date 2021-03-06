package com.iresearch.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iresearch.constant.ProjectEnum;
import com.iresearch.constant.RoleEnum;
import com.iresearch.entity.Operation;
import com.iresearch.entity.Project;
import com.iresearch.entity.User;
import com.iresearch.service.IOperationService;
import com.iresearch.service.IProjectService;
import com.iresearch.service.IUserService;
import com.iresearch.vo.ProjectVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
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

    @Autowired
    IOperationService iOperationService;

    /**
     * 获取全部项目
     * @param response response
     */
    @RequiresRoles(value = {"user","admin"},logical = Logical.OR)
    @GetMapping
    public List<ProjectVO> getProjects(HttpServletResponse response){
        return getProjectsSearch("", response);
    }
    /**
     * 查询项目 (Shiro PathVariable 中文)
     * @param response response
     */
    @RequiresRoles(value = {"user","admin"},logical = Logical.OR)
    @GetMapping("/name")
    public List<ProjectVO> getProjectsSearch(String name,
                                             HttpServletResponse response){

        // 权限判断
        final String username = (String) SecurityUtils.getSubject().getPrincipal();
        final String userTypeString = iUserService.getUserTypeStringByName(username);

        final List<Project> projects;

        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Project::getName,name);
        // 用户只能获取自己的 项目
        if (RoleEnum.USER.value().equals(userTypeString)){
            final User user = iUserService.getUserByName(username);
            wrapper.eq(Project::getCreateId,user.getId());
            projects = iProjectService.list(wrapper);

        } else {
            projects = iProjectService.list(wrapper);
        }
        if(projects == null || projects.size() == 0){
            //  暂不返回 404 错误
            // response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.OK.value());
            return iProjectService.projectList2ProjectVOList(projects);
        }
        return null;
    }

    /**
     * 通过Id 获取项目
     * @param  id ProjectId
     * @return Project
     */
    @RequiresRoles(value = {"user","admin"},logical = Logical.OR)
    @GetMapping("/id/{id}")
    public ProjectVO getProjectById( @PathVariable Integer id,HttpServletResponse response){
        final Project project = iProjectService.getById(id);
        if (project == null){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
        // 判断权限
        final String username = (String) SecurityUtils.getSubject().getPrincipal();
        final String typeString = iUserService.getUserTypeStringByName(username);
        final boolean own = iProjectService.isOwnByUserName(id, username);
        // 当前角色是用户 且 项目创建者不是 当前用户
        if (RoleEnum.USER.value().equals(typeString) && !own){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }
        response.setStatus(HttpStatus.OK.value());
        return iProjectService.project2ProjectVO(project);
    }

    /**
     * 通过状态获取 项目
     */
    @RequiresRoles(value = {"user","admin"},logical = Logical.OR)
    @GetMapping("/status/{status}")
    public List<ProjectVO> getProjectsByStatus(@PathVariable Integer status,
                                             HttpServletResponse response){
        return getProjectsByStatusSearch(status, "", response);

    }

    /**
     * 通过状态 查询 项目
     */
    @RequiresRoles(value = {"user","admin"},logical = Logical.OR)
    @GetMapping("/status/{status}/name")
    public List<ProjectVO> getProjectsByStatusSearch(@PathVariable Integer status, String name,
                                                     HttpServletResponse response){

        final String username = (String) SecurityUtils.getSubject().getPrincipal();
        final String userTypeString = iUserService.getUserTypeStringByName(username);

        final List<Project> projects;
        // 用户只能获取自己的 项目
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getProjectStatus,status);
        wrapper.like(Project::getName,name);

        if (RoleEnum.USER.value().equals(userTypeString)){
            final User user = iUserService.getUserByName(username);
            wrapper.eq(Project::getCreateId,user.getId());
        }

        projects = iProjectService.list(wrapper);
        if(projects == null || projects.size() == 0){
            //  暂不返回 404 错误
            // response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.OK.value());
            return iProjectService.projectList2ProjectVOList(projects);
        }
        return null;
    }

    /**
     * 添加项目
     */
    @RequiresRoles(value = {"user","admin"},logical = Logical.OR)
    @PostMapping
    public void addProject(String name, Integer subject,String inCharge, Integer type,
                           String projectPurpose,String economicAnalysis, String existingConditions,
                           String expectedResult, String viableAnalysis,
                           HttpServletResponse response){

        Project project = new Project();
        projectSetter(name, subject, inCharge, type, projectPurpose, economicAnalysis, existingConditions, expectedResult, viableAnalysis,
                project);
        // 获取当前用户id
        final String username = (String) SecurityUtils.getSubject().getPrincipal();
        final User user = iUserService.getUserByName(username);
        final int userId = user.getId();

        project.setCreateId(userId);
        project.setCreateTime(LocalDateTime.now());
        project.setProjectStatus(ProjectEnum.CREATED.value());
        // 保存到数据库
        final boolean save = iProjectService.save(project);
        if (save){
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        // 操作记录
        Operation operation =new Operation();
        operation.setUserId(userId);
        operation.setProjectId(project.getId());
        operation.setOperation(ProjectEnum.CREATED.explain());
        operation.setCreateTime(LocalDateTime.now());
        iOperationService.save(operation);
    }

    /**
     * 删除项目
     */
    @RequiresRoles(value = {"user","admin"},logical = Logical.OR)
    @DeleteMapping("/{id}")
    public void deleteProjectById(@PathVariable Integer id,
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
        // 当前角色是用户 且 不是 项目创建者 无权修改
        if (RoleEnum.USER.value().equals(typeString) && !iProjectService.isOwnByUserName(id, username)){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        // 删除
        iProjectService.updateProjectStatus(id,ProjectEnum.DELETED.value());
        response.setStatus(HttpStatus.OK.value());
        // 操作记录
        Operation operation =new Operation();
        final User user = iUserService.getUserByName(username);
        operation.setUserId(user.getId());
        operation.setProjectId(id);
        operation.setCreateTime(LocalDateTime.now());
        operation.setOperation(ProjectEnum.DELETED.explain());
        iOperationService.save(operation);
    }

    /**
     * 修改项目
     */
    @RequiresRoles(value = {"user","admin"},logical = Logical.OR)
    @PutMapping("/{id}")
    public void updateProjectById(@PathVariable Integer id,
                                  String name, Integer subject,String inCharge, Integer type,
                                  String projectPurpose,String economicAnalysis, String existingConditions,
                                  String expectedResult, String viableAnalysis,
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
        // 当前角色是用户 且 不是 项目创建者 无权修改
        if (RoleEnum.USER.value().equals(typeString) && !iProjectService.isOwnByUserName(id, username)){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        // 修改项目
        projectSetter(name, subject, inCharge, type, projectPurpose, economicAnalysis, existingConditions, expectedResult, viableAnalysis,
                project);
        project.setUpdateTime(LocalDateTime.now());
        // 保存数据库
        final boolean save = iProjectService.updateById(project);
        if (save) {
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        // 操作记录
        Operation operation =new Operation();
        final User user = iUserService.getUserByName(username);
        operation.setUserId(user.getId());
        operation.setProjectId(id);
        operation.setCreateTime(LocalDateTime.now());
        operation.setOperation(ProjectEnum.UPDATE.explain());
        iOperationService.save(operation);
    }

    /**
     * 修改项目状态
     */
    @PutMapping("/{id}/status/{status}")
    @RequiresRoles(value = {"user","admin"},logical = Logical.OR)
    public void changeStatus(@PathVariable Integer id,@PathVariable Integer status,
                              HttpServletResponse response){
        //合法性判断
        // 查找项目
        final Project project = iProjectService.getById(id);
        if (project == null){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        // 查找状态
        ProjectEnum projectEnum = null;
        for (ProjectEnum p : ProjectEnum.values()) {
            if (p.value().equals(status)){
                projectEnum = p;
            }
        }
        if (projectEnum == null){
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        // 判断权限
        final String username = (String) SecurityUtils.getSubject().getPrincipal();
        final String typeString = iUserService.getUserTypeStringByName(username);
        // 当前角色是用户 且 不是 项目创建者 无权修改
        if (RoleEnum.USER.value().equals(typeString) && !iProjectService.isOwnByUserName(id, username)){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        // 当前角色是 用户 且 是 创造者
        else if (RoleEnum.USER.value().equals(typeString)){
            // 提交合法性判断
            // 只有两种情况可以 能够更改
            final Integer projectStatus = project.getProjectStatus();
            // 1. project 状态为 created 修改 为 applying
            final boolean case1 = ProjectEnum.CREATED.value().equals(projectStatus) && ProjectEnum.APPLYING.value().equals(status);
            // 2. project 状态为 apply 修改 为 ending
            final boolean case2 = ProjectEnum.APPLY.value().equals(projectStatus) && ProjectEnum.ENDING.value().equals(status);
            // 两种情况都不满足 时 则 抛出异常
            if (!case1 && !case2){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }
        // 当前角色是 管理员 或 用户 合法时
        final boolean update = iProjectService.updateProjectStatus(id, status);
        if (update){
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        // 操作记录
        Operation operation =new Operation();
        final User user = iUserService.getUserByName(username);
        operation.setUserId(user.getId());
        operation.setProjectId(id);
        operation.setOperation(projectEnum.explain());
        operation.setCreateTime(LocalDateTime.now());
        iOperationService.save(operation);
    }

    /**
     * 简化代码
     */
    private void projectSetter(String name, int subject, String inCharge, int type, String projectPurpose, String economicAnalysis, String existingConditions, String expectedResult, String viableAnalysis, @NotNull Project project) {
        project.setName(name);
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
