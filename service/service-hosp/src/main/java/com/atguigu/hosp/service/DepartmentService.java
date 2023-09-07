package com.atguigu.hosp.service;

import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.DepartmentVo;

import java.util.List;
import java.util.Map;

public interface DepartmentService {

    void saveDepartment(Map<String, Object> stringObjectMap);

    Map<String,Object> getDepartmentPage(Integer pageNum, Integer pageSize, DepartmentQueryVo departmentQueryVo);

    void remove(String hoscode, String depcode);

    List<DepartmentVo> findDeptTree(String hoscode);

    void getDepartmentByHoscode(String hoscode);
}
