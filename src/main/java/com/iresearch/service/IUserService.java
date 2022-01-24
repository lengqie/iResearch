package com.iresearch.service;

import com.iresearch.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lengqie
 * @since 2022-01-20
 */
public interface IUserService extends IService<User> {


    /**
     * 通过用户名获取用户
     * @param name
     * @return
     */

    User getUserByName(String name);
    /**
     * 通过用户名 获取用户类型
     * @param name
     * @return
     */
    String getUserTypeStringByName(String name);

}
