package com.atguigu.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.hosp.repository.HospitalRepository;
import com.atguigu.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;


@Service
public class HospitalServiceImpl implements HospitalService {

    @Resource
    private HospitalRepository hospitalRepository;


    @Override
    public void saveHospital(Map paramMap) {
        String param = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(param, Hospital.class);
        Hospital exist = hospitalRepository.getHospitalByHoscode(hospital.getHoscode());

        if (exist != null) {
            hospital.setStatus(exist.getStatus());
            hospital.setCreateTime(exist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
            return;
        }
        hospital.setStatus(0);
        hospital.setCreateTime(new Date());
        hospital.setUpdateTime(new Date());
        hospital.setIsDeleted(0);
        hospitalRepository.save(hospital);
    }
}
