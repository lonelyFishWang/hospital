package com.atguigu.yygh.controller;


import com.atguigu.common.result.Result;
import com.atguigu.yygh.service.HospitalService;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HospitalController {

    @Resource
    private HospitalService hospitalService;


    @GetMapping("list/{page}/{limit}")
    public Result getHospitalList(@PathVariable Integer page,
                                  @PathVariable Integer limit,
                                  HospitalQueryVo hospitalQueryVo) {

        hospitalService.selectPage(page,limit,hospitalQueryVo);
        return null;
    }
}
