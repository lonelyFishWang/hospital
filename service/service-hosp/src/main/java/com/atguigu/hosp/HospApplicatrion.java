package com.atguigu.hosp;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@MapperScan(basePackages = "com.atguigu.hosp.mapper")
@SpringBootApplication
public class HospApplicatrion {
    public static void main(String[] args) {
        SpringApplication.run(HospApplicatrion.class, args);
    }
}
