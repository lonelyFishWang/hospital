package com.atguigu.hosp.controller;


import com.atguigu.common.result.Result;
import com.atguigu.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/admin/hospset/hospitalSet")
public class HospitalSetController {
    @Resource
    private HospitalSetService hospitalSetService;

    @GetMapping("")
    public Result getHospitalSetInfo(String id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.success();
    }


    @PostMapping("/{current}/{limit}")
    public Result findAllHospitalSet(@PathVariable Integer current,
                                     @PathVariable Integer limit,
                                     @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        Page<HospitalSet> page = new Page<>();
        page.setCurrent(current);
        page.setSize(limit);
        QueryWrapper<HospitalSet> qw = new QueryWrapper();
        if (hospitalSetQueryVo != null) {
            if (!StringUtils.isEmpty(hospitalSetQueryVo.getHoscode())) {
                qw.eq("hoscode", hospitalSetQueryVo.getHoscode());
            }
            if (!StringUtils.isEmpty(hospitalSetQueryVo.getHosname())) {
                qw.like("hosname", hospitalSetQueryVo.getHosname());
            }
        }
        page = hospitalSetService.page(page, qw);
        return Result.success(page);
    }
}
