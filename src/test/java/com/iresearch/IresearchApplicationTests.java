package com.iresearch;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iresearch.entity.User;
import com.iresearch.mapper.UserMapper;
import com.iresearch.service.IUserService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IresearchApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        final User user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Autowired
    IUserService iUserService;
    @Test
    void wrapper(){
        final LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        wrapper.eq(User::getName,"username")
                .eq(User::getPassword,"password");
        final User one = iUserService.getOne(wrapper);
        System.out.println(one);

    }

}
