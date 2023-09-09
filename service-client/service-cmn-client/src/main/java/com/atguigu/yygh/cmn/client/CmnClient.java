package com.atguigu.yygh.cmn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Repository
@FeignClient(value = "service-cmn")
public interface CmnClient {

    @GetMapping(value = "/admin/cmn/dict/getName/{parentDictCode}/{value}")
    String getDictName(@PathVariable("parentDictCode") String parentDictCode, @PathVariable("value") String value);


    @GetMapping(value = "/admin/cmn/dict/getName/{value}")
    String getDictName(@PathVariable("value") String value);

}
