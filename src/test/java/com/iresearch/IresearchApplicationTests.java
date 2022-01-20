package com.iresearch;

import com.iresearch.entity.User;
import com.iresearch.mapper.UserMapper;
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

}
