package com.info.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan(value = "com.info.manage.dao")
@EnableAspectJAutoProxy  // 切面实现 ，不加 进入不到 ControllerValidatorAspect 切面
public class InfoWebApplication {
    private static  final Logger logger = LoggerFactory.getLogger(InfoWebApplication.class);
    public static void main(String[] args) {
        logger.info("启动项目，加载类开始....");
        SpringApplication.run(InfoWebApplication.class, args);
        logger.info("启动项目，加载类结束....");
    }
}
