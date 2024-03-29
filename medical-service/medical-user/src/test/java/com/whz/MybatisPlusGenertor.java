package com.whz;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @author 文辉正
 * @since 2023/3/3 21:17
 */
public class MybatisPlusGenertor {

    public static void main(String[] args) {
        String url = "jdbc:mysql://192.168.88.131:3306/medicine_platform?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        FastAutoGenerator.create(url, "root", "wfz123")
                .globalConfig(builder -> {
                    builder.author("文辉正") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("medical-service\\medical-care\\src\\main\\java"); // 指定输出目录
                    //
                })
                .packageConfig(builder -> {
                    builder.parent("com.whz") // 设置父包名
                            //.moduleName("/") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml
                                    , "medical-service\\medical-care\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("forum_collection","forum_comment") // 设置需要生成的表名
                            //.addInclude("medicine_symptom")
                            .addTablePrefix("medicine_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
