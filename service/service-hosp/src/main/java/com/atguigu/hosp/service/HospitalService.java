package com.atguigu.hosp.service;

import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface HospitalService {

    void saveHospital(Map<String, Object> paramMap);


    Hospital show(String hoscode);

    Page<Hospital> selectPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);


    List<Hospital> findHospitalByHosnameLike(String hosname);


    Map<String,Object> item(String hoscode);
}
