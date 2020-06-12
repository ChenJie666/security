package com.cj.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author: CJ
 * @Data: 2020/6/8 16:30
 */
@SpringBootApplication/*(exclude = {DataSourceAutoConfiguration.class})*/
@MapperScan(basePackages = "com.cj.security.auth")
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
