package com.iresearch.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.fasterxml.jackson.core.SerializableString;

import java.util.Collections;

/**
 * @author lengqie
 */
public class Genertaor {
    public static void main(String[] args) {
        final String url = "jdbc:mysql://localhost:3306/iresearch?useSSL=false";
        final String parent = "com";
        final String module = "iresearch";
        final String name = "lengqie";

        FastAutoGenerator.create(url, "root", "")
                .globalConfig(builder -> {
                    builder.author(name) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("\\src\\mai\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(parent) // 设置父包名
                            .moduleName(module) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "\\src\\main\\resources\\com\\iresearch\\mapper")); // 设置mapperXml生成路径
                })
                .execute();

    }
}
