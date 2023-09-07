package com.atguigu.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.cmn.mapper.DictMapper;
import com.atguigu.cmn.service.DictService;
import com.atguigu.cmn.util.ExcelLisoner;
import com.atguigu.yygh.common.util.RedisUtil;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//实现接口这是把这个指令加入到指令集合中 继承就是把指令数据 覆盖 重新指向子类 通常
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Resource
    private DictMapper dictMapper;

    @Resource
    private BaseMapper<Dict> baseMapper;

    @Resource
    private RedisUtil redisUtil;

    // 这个指令集合 中还有指令分支
    @Override
//    @Cacheable("")
    public List<Dict> findDictChildren(Long id) {

        //        先去redis中取


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
    public void exprotExcel(HttpServletResponse response) {
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

//            这个代表把数据写入到response对象的输出流中
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict").doWrite(dictEeVos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //    导入excle
    @Override
    public boolean importExcel(MultipartFile file) {
        try {
//                excelLisoner负责解析excel 从file 输入流中拿到excel
            EasyExcel.read(file.getInputStream(), DictEeVo.class, new ExcelLisoner(dictMapper)).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    @Override
    public String getDictName(String dictCode, String value) {


        if (StringUtils.isEmpty(dictCode)) {
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("value", value);
            Dict dict = baseMapper.selectOne(wrapper);
            return dict.getName();
        } else {
            Dict codeDict = this.getDictByDictCode(dictCode);
            Long parent_id = codeDict.getId();
            Dict finalDict = baseMapper.selectOne(new QueryWrapper<Dict>()
                    .eq("parent_id", parent_id)
                    .eq("value", value));
            return finalDict.getName();
        }
    }

    private Dict getDictByDictCode(String dictCode) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code", dictCode);
        Dict dict = dictMapper.selectOne(wrapper);
        return dict;
    }


    //   是否包含子节点
    public boolean whetherChildren(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }
}
