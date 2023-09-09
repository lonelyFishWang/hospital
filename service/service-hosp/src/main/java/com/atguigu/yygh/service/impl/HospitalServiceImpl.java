package com.atguigu.yygh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.service.HospitalService;
import com.atguigu.yygh.repository.HospitalRepository;
import com.atguigu.yygh.cmn.client.CmnClient;
import com.atguigu.yygh.model.hosp.BookingRule;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class HospitalServiceImpl implements HospitalService {

    @Resource
    private HospitalRepository hospitalRepository;


//    @Autowired
    private CmnClient cmnClient;


    @Override
    public void saveHospital(Map<String, Object> paramMap) {
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

    @Override
    public Hospital show(String hoscode) {
        return hospitalRepository.getHospitalByHoscode(hoscode);
    }

    @Override
    public  Page<Hospital> selectPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {

        Pageable pageable = PageRequest.of(page - 1, limit);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo, hospital);
        Example<Hospital> example = Example.of(hospital, exampleMatcher);
        Page<Hospital> pageHospital = hospitalRepository.findAll(example, pageable);

        pageHospital.getContent().stream().forEach(hosp -> {
           this.getDictName(hosp);
        });

        return pageHospital;
    }

    @Override
    public List<Hospital> findHospitalByHosnameLike(String hosname) {
        return hospitalRepository.findHospitalByHosnameLike(hosname);
    }



//     根据医院编号获取医院预约挂号详情
    @Override
    public Map<String, Object> item(String hoscode) {
        HashMap<String, Object> result = new HashMap<>();
        Hospital hospitalByHoscode = hospitalRepository.getHospitalByHoscode(hoscode);
        Hospital dictName = this.getDictName(hospitalByHoscode);
        BookingRule bookingRule = dictName.getBookingRule();
        result.put("hospital", dictName);
        result.put("bookingRule",bookingRule);
        dictName.setBookingRule(null);
        return result;
    }





    //获取查询list集合，遍历进行医院等级封装
    private Hospital getDictName(Hospital hospital) {
        String hostypeString = cmnClient.getDictName(hospital.getHostype());
        String provinceString = cmnClient.getDictName(hospital.getProvinceCode());
        String cityString = cmnClient.getDictName(hospital.getCityCode());
        String districtString = cmnClient.getDictName(hospital.getDistrictCode());

        hospital.getParam().put("fullAddress",provinceString+cityString+districtString);
        hospital.getParam().put("hostypeString",hostypeString);


        return hospital;
    }
}
