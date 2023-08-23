package com.atguigu.cmn;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.atguigu.cmn.mapper")
@ComponentScan("com.atguigu")
public class CmnApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmnApplication.class, args);
    }
}
