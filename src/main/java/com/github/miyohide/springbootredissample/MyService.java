package com.github.miyohide.springbootredissample;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    private final RedisTemplate<String, String> redisTemplate;

    public MyService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setData() {
        redisTemplate.opsForValue().set("my_key2", "my_value");
    }

    public void getData() {
        String val = redisTemplate.opsForValue().get("my_key2").toString();
        System.out.println("----- Redis data is = [" + val + "] -----");
    }
}
