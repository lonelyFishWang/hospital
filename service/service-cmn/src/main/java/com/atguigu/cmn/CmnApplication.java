package com.atguigu.cmn;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.atguigu.cmn.mapper")
public class CmnApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmnApplication.class, args);
    }
}
