package com.atguigu.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.hosp.repository.DepartmentRepository;
import com.atguigu.hosp.service.DepartmentService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;


@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentRepository departmentRepository;
    @Override
    public void saveDepartment(Map<String, Object> departmentMap) {
        String param = JSONObject.toJSONString(departmentMap);
        Department department = JSONObject.parseObject(param, Department.class);
        Hospital exist = departmentRepository.getDepartmentByHoscode(department.getHoscode());

        if (exist != null) {
            department.setCreateTime(exist.getCreateTime());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
            return;
        }
        department.setCreateTime(new Date());
        department.setUpdateTime(new Date());
        department.setIsDeleted(0);
        departmentRepository.save(department);

    }
}
