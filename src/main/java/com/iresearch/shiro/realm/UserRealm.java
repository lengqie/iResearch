package com.iresearch.shiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iresearch.entity.User;
import com.iresearch.entity.UserType;
import com.iresearch.service.IUserService;
import com.iresearch.service.IUserTypeService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

/**
 * @author lengqie
 * @introduction
 */
public class UserRealm extends AuthorizingRealm {

    final Logger logger = LoggerFactory.getLogger(UserRealm.class);


    @Autowired
    IUserService iUserService;

    @Autowired
    IUserTypeService iUserTypeService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("Authorization...");

        final String username = (String) principalCollection.getPrimaryPrincipal();

        // 获取User.userType
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,username);
        final User user = iUserService.getOne(wrapper);
        final Integer userTypeId = user.getUserType();

        // 获取UserType.Type
        final UserType userType = iUserTypeService.getById(userTypeId);
        final String type = userType.getType();

        final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // HashSet匿名内部类
        info.setRoles(new HashSet<String>(){{add(type);}});

        logger.info("✔授权成功...");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("Authentication...");
        //
        final UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        final String username = token.getUsername();
        final String password = new String(token.getPassword());
        final LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,username)
                .eq(User::getPassword,password);
        final User user = iUserService.getOne(wrapper);
        if (user == null){
            throw new AuthenticationException("account or password error\n");
        }
        logger.info("✔登录成功...");
        return new SimpleAuthenticationInfo(token.getPrincipal(),password,this.getName());
    }
}
