package com.atguigu.yygh.service.impl;

import com.atguigu.yygh.mapper.HospitalSetMapper;
import com.atguigu.yygh.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wangfuyu
 * @description 针对表【hospital_set(医院设置表)】的数据库操作Service实现
 * @createDate 2023-08-11 09:47:05
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet>
        implements HospitalSetService {


    @Resource
    private HospitalSetMapper hospitalSetMapper;

    @Override
    public String getSignByHoscode(String hoscode) {
        QueryWrapper<HospitalSet> qw = new QueryWrapper<>();
        qw.eq("hoscode", hoscode);
        HospitalSet hospitalSet = hospitalSetMapper.selectOne(qw);
        return hospitalSet.getSignKey();
    }
}




