package com.atguigu.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.cmn.mapper.DictMapper;
import com.atguigu.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//实现接口这是把这个指令加入到指令集合中 继承就是把指令数据 覆盖 重新指向子类 通常
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Resource
    private BaseMapper<Dict> baseMapper;

    // 这个指令集合 中还有指令分支
    @Override
    public List<Dict> findDictChildren(Long id) {
//        创建一个指针指向qw 用于掉哟个qw中的指令
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        List<Dict> dicts = baseMapper.selectList(queryWrapper);
        dicts.forEach(dict -> {
            Long dictId = dict.getId();
            boolean whetherChildren = whetherChildren(dictId);
            dict.setHasChildren(whetherChildren);
        });
        return dicts;
    }

    //   执行导出
    @Override
    public boolean exprotExcel(HttpServletResponse response) {
        boolean result = true;
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf8");
        String fileName = "dict";
        response.setHeader("Content-dispostion", "attachment;filename=" + fileName + ".xlsx");

        List<Dict> dicts = baseMapper.selectList(null);
        ArrayList<DictEeVo> dictEeVos = new ArrayList<>();

        dicts.forEach(dict -> {
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict, dictEeVo);
            dictEeVos.add(dictEeVo);
        });
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet().doWrite(dictEeVos);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }


        return result;
    }

    //   是否包含子节点
    public boolean whetherChildren(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }
}
