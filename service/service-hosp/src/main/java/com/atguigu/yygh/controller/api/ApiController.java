package com.atguigu.yygh.controller.api;


import com.atguigu.common.exception.YyghException;
import com.atguigu.common.result.Result;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.yygh.service.DepartmentService;
import com.atguigu.yygh.service.HospitalService;
import com.atguigu.yygh.service.HospitalSetService;
import com.atguigu.yygh.service.ScheduleService;
import com.atguigu.yygh.common.helper.HttpRequestHelper;
import com.atguigu.yygh.common.util.MD5;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Resource
    private HospitalService hospitalService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private HospitalSetService hospitalSetService;

    @Resource
    private ScheduleService scheduleService;




    //    添加医院基本信息
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
    public Result saveDepartment(HttpServletRequest request) {

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


    @PostMapping("/department/list")
    public Result getDepartmentPage(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);
        try {
            verifySignature(stringObjectMap);
        } catch (Exception e) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        int pageNum = stringObjectMap.isEmpty() ? 1 : Integer.parseInt((String) stringObjectMap.get("pageNum"));
        int pageSize = stringObjectMap.isEmpty() ? 1 : Integer.parseInt((String) stringObjectMap.get("pageSize"));

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode((String) stringObjectMap.get("hoscode"));

        Map<String, Object> departmentPage = departmentService.getDepartmentPage(pageNum, pageSize, departmentQueryVo);
        return Result.ok(departmentPage);
    }


    @PostMapping("/saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);
        try {
            verifySignature(stringObjectMap);
        } catch (YyghException e) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        scheduleService.saveSchedule(stringObjectMap);
        return Result.ok();
    }


    @PostMapping("/hospital/show")
    public Result ShowHospital(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);
        try {
            verifySignature(stringObjectMap);
        } catch (YyghException e) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        Hospital hospital = hospitalService.show((String) stringObjectMap.get("hoscode"));
        return Result.ok(hospital);
    }

    @PostMapping("/schedule/list")
    public Result getScheduleLsit(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);
        try {
            verifySignature(stringObjectMap);
        } catch (Exception e) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        int pageNum = stringObjectMap.isEmpty() ? 1 : Integer.parseInt((String) stringObjectMap.get("pageNum"));
        int pageSize = stringObjectMap.isEmpty() ? 1 : Integer.parseInt((String) stringObjectMap.get("pageSize"));

        Map<String, Object> schedulePage = scheduleService.getSchedulePage(pageNum, pageSize);
        return Result.ok(schedulePage);
    }


    @PostMapping("/department/remove")
    public Result removeDepartment(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);

        try {
            verifySignature(stringObjectMap);
        } catch (Exception e) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        departmentService.remove((String) stringObjectMap.get("hoscode"), (String) stringObjectMap.get("depcode"));

        return Result.ok();
    }

    @PostMapping("/schedule/remove")
    public Result removeSchedule(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);

        try {
            verifySignature(stringObjectMap);
        } catch (Exception e) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.remove((String)stringObjectMap.get("hoscode"),(String)stringObjectMap.get("hosScheduleId"));

        return null;
    }




    //验证签名的方法
    private void verifySignature(Map departmentMap) {
        String paramSign = (String) departmentMap.get("sign");
        String hosSign = hospitalSetService.getSignByHoscode((String) departmentMap.get("hoscode"));
        String encrypt = MD5.encrypt(hosSign);
        if (!paramSign.equals(encrypt)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
    }


}
