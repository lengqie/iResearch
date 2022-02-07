package com.iresearch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iresearch.entity.User;
import com.iresearch.entity.UserType;
import com.iresearch.mapper.UserMapper;
import com.iresearch.mapper.UserTypeMapper;
import com.iresearch.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iresearch.vo.UserVO;
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
        userTypeWrapper.eq(UserType::getId,typeId);
        final UserType userType = userTypeMapper.selectOne(userTypeWrapper);

        return userType.getType();
    }

    @Override
    public UserVO user2userVO(User user) {
        UserVO userVO =  new UserVO();
        userVO.setId(user.getId());
        userVO.setName(user.getName());
        userVO.setNickname(user.getNickname());
        userVO.setType(getUserTypeStringByName(user.getName()));
        userVO.setCreateTime(user.getCreateTime());
        return userVO;
}

    @Override
    public List<UserVO> userList2userVOList(List<User> users) {
        List<UserVO> userVOs = new ArrayList<>();
        for (User user : users) {
            userVOs.add(user2userVO(user));
        }
        return userVOs;
    }

}
