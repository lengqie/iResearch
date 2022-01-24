package com.iresearch.service;

import com.iresearch.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param id
     * @param status
     * @return
     */
    boolean updateProjectStatus(Integer id,Integer status);

    /**
     * 判断用户是否具有项目所有权
     * @param id
     * @param name
     * @return
     */
    boolean isOwnByUserName(Integer id, String name);
}
