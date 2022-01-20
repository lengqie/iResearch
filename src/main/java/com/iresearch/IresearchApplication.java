package com.iresearch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.iresearch.mapper")
public class IresearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(IresearchApplication.class, args);
    }

}
