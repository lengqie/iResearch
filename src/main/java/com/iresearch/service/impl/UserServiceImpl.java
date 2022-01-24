package com.iresearch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iresearch.entity.User;
import com.iresearch.entity.UserType;
import com.iresearch.mapper.UserMapper;
import com.iresearch.mapper.UserTypeMapper;
import com.iresearch.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    UserMapper userMapper;

    @Resource
    UserTypeMapper userTypeMapper;

    @Override
    public User getUserByName(String name) {
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getName,name);
        return userMapper.selectOne(userWrapper);
    }

    @Override
    public String getUserTypeStringByName(String name) {

        final User user = getUserByName(name);

        final Integer typeId = user.getUserType();
        LambdaQueryWrapper<UserType> userTypeWrapper = new LambdaQueryWrapper<>();
        userTypeWrapper.eq(UserType::getType,typeId);
        final UserType userType = userTypeMapper.selectOne(userTypeWrapper);

        return userType.getType();
    }

}
