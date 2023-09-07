package com.atguigu.hosp;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@MapperScan(basePackages = "com.atguigu.hosp.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class HospApplicatrion {
    public static void main(String[] args) {
        SpringApplication.run(HospApplicatrion.class, args);
    }
}
