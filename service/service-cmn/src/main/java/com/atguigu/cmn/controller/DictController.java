package com.atguigu.cmn.controller;


import com.atguigu.cmn.service.DictService;
import com.atguigu.common.result.Result;
import com.atguigu.yygh.model.cmn.Dict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/admin/cmn/dict")
public class DictController {

    @Resource
    private DictService dictService;

    @GetMapping("/findChildData/{id}")
    public Result getDictList(@PathVariable Long id) {

//        dictServic是一个指向另一个指令的地址 并且执行findDictChildren指令并且把数据id传给这个指令
        List<Dict> dicts = dictService.findDictChildren(id);
        return Result.success(dicts);
    }

//    导出为excel

    @GetMapping("/exportData")
    public void exprotExcel(HttpServletResponse response){
      dictService.exprotExcel(response);

    }

    @PostMapping("/importData")
    public Result importExcel(MultipartFile file){
        boolean success = dictService.importExcel(file);
        if (success){
            return Result.success();
        }
        return Result.fail();
    }
}
