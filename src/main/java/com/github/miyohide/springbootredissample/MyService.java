package com.github.miyohide.springbootredissample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void setData() {
        redisTemplate.opsForValue().set("my_key", "my_value");
    }

    public void getData() {
        String val = redisTemplate.opsForValue().get("my_key").toString();
        System.out.println("----- Redis data is = [" + val + "] -----");
    }
}
