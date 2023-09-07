package com.atguigu.hosp.controller.api;


import com.atguigu.common.result.Result;
import com.atguigu.hosp.service.DepartmentService;
import com.atguigu.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/hosp/hospital")
public class HospitalApiController {

    @Resource
    private HospitalService hospitalService;
    @Resource
    private DepartmentService departmentService;

   @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Integer page,
                                @PathVariable Integer limit){
       Page<Hospital> hospitals = hospitalService.selectPage(page, limit, null);


       return Result.ok(hospitals);
    }

    @GetMapping("findByHosname/{hosname}")
    public Result findByHosname(@PathVariable String hosname) {

//       根据医院名字查询

        return Result.ok(hospitalService.findHospitalByHosnameLike(hosname));
    }


//    获取科室列表
    @GetMapping("department/{hoscode}")
    public Result index(@PathVariable String hoscode){

        departmentService.getDepartmentByHoscode(hoscode);

        return null;
    }

//    根据医院编号获取医院预约挂号详情

    @GetMapping("findHospDetail/{hoscode}")
    public Result item(@PathVariable String hoscode){

        hospitalService.item(hoscode);


       return  null;
    }




//       获取可以预约的排版信息
    @GetMapping("auth/getBookingScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getBookingSchedule(){


        return null;
    }



//       获取排班数据
    @GetMapping("auth/findScheduleList/{hoscode}/{depcode}/{workDate}")
    public Result findScheduleList(){



       return null;
    }


}
