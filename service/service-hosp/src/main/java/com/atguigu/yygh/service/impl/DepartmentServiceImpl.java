package com.atguigu.yygh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.repository.DepartmentRepository;
import com.atguigu.yygh.service.DepartmentService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public Map<String, Object> getDepartmentPage(Integer pageNum, Integer pageSize, DepartmentQueryVo departmentQueryVo) {

        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);

        Example<Department> example = Example.of(department, matcher);

        Pageable of = PageRequest.of(pageNum - 1, pageSize);



        Page<Department> all = departmentRepository.findAll(example,of);

        List<Department> content = all.getContent();
        int totalPages = all.getTotalPages();
        Map<String, Object> DepartmentMap = new HashMap<>();
        DepartmentMap.put("content", content);
        DepartmentMap.put("total", totalPages);
        return DepartmentMap;
    }



    @Override
    public void remove(String hoscode, String depcode) {
        Department department = new Department();
        department.setDepcode(depcode);
        department.setHoscode(hoscode);
        departmentRepository.delete(department);
    }

    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        return null;
    }


    @Override
    public void getDepartmentByHoscode(String hoscode) {
        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        Example example = Example.of(departmentQueryVo);
        departmentRepository.findAll(example);
    }
}
