package com.atguigu.hosp.service;

import com.atguigu.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author wangfuyu
* @description 针对表【hospital_set(医院设置表)】的数据库操作Service
* @createDate 2023-08-11 09:47:05
*/


@Service
public interface HospitalSetService extends IService<HospitalSet> {


    String getSignByHoscode(String hoscode);
}
