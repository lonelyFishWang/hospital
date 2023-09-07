package com.atguigu.cmn.service;

import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


//接口就是用来封装指令一个数据  数据内容就是指令
public interface DictService extends IService<Dict> {
    //到这个指令集合中找到finddictchildren指令 这个只是代表指令的地址 会指向真正执行的指令
    List<Dict> findDictChildren(Long id);

    void exprotExcel(HttpServletResponse response);

    boolean importExcel(MultipartFile file);


    String getDictName(String dictCode, String value);
}
