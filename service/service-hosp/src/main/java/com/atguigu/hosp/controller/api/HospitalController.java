package com.atguigu.hosp.controller.api;


import com.atguigu.common.exception.YyghException;
import com.atguigu.common.result.Result;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.hosp.service.DepartmentService;
import com.atguigu.hosp.service.HospitalService;
import com.atguigu.hosp.service.HospitalSetService;
import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.util.MD5;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("/api/hosp")
public class HospitalController {
    @Resource
    private HospitalService hospitalService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private HospitalSetService hospitalSetService;

    //    添加医院基本信息
    @CrossOrigin
    @PostMapping("/saveHospital")
    public Result<Object> saveHospital(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(parameterMap);
        String paramSign = (String) paramMap.get("sign");
        String hosSign = hospitalSetService.getSignByHoscode((String) paramMap.get("hoscode"));
        String encrypt = MD5.encrypt(hosSign);

        if (!paramSign.equals(encrypt)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        String logoData = (String) paramMap.get("logoData");
        logoData = logoData.replace("", "+");
        paramMap.put("logoData", logoData);

        hospitalService.saveHospital(paramMap);
        return Result.ok();
    }

    @PostMapping("/saveDepartment")
    public Result saveDepartment(HttpServletRequest request){

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> departmentMap = HttpRequestHelper.switchMap(parameterMap);

//        验签
        String paramSign = (String) departmentMap.get("sign");
        String hosSign = hospitalSetService.getSignByHoscode((String) departmentMap.get("hoscode"));
        String encrypt = MD5.encrypt(hosSign);

        if (!paramSign.equals(encrypt)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        departmentService.saveDepartment(departmentMap);
        return Result.ok();
    }
}
