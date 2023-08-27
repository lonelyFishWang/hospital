package com.atguigu.hosp.controller.api;


import com.atguigu.common.result.Result;
import com.atguigu.hosp.service.HospitalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController("/api/hosp")
public class HospitalController {
    @Resource
    private HospitalService hospitalService;
//    添加医院基本信息


    @PostMapping("/saveHospital")
    public Result saveHospital(HttpServletRequest request) {


        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap != null){
            hospitalService.saveHospital(parameterMap);
        }
        return null;
    }
}
