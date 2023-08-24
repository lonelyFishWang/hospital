package com.atguigu.yygh.common.util;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {

    private RedisTemplate redisTemplate;
    private ValueOperations valueOperations;

    public RedisUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }
// 根据key取
    public Object getBykey(String key){
        return valueOperations.get(key);
    }

}
