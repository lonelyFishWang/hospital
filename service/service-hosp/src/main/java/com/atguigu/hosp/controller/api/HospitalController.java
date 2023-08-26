package com.atguigu.hosp.controller.api;


import com.atguigu.common.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/hosp")
public class HospitalController {

//    添加医院基本信息


    @PostMapping("/saveHospital")
    public Result saveHospital(){

        return null;
    }
}
