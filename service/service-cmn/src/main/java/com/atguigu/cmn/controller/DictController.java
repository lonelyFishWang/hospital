package com.atguigu.cmn.controller;


import com.atguigu.cmn.service.DictService;
import com.atguigu.common.result.Result;
import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/cmn/dict")
public class DictController {

    @Resource
    private DictService dictService;

    @GetMapping("/findChildData/{id}")
    public Result getDictList(@PathVariable Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        List<Dict> dicts = dictService.list(queryWrapper);

        if (dicts.size() == 0){
            return Result.fail();
        }
        return Result.success(dicts);
    }
}
