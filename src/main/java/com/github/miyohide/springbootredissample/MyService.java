package com.github.miyohide.springbootredissample;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MyService {
    private final RedisTemplate<String, String> redisTemplate;

    public MyService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setData() {
        for (int i = 0; i < 1_000; i++) {
            String k = String.format("my_key_%05d", i);
            String v = String.format("my_val_%05d", i);
            redisTemplate.opsForValue().set(k, v);
        }
    }

    public void getData() {
        Set<String> keys = redisTemplate.keys("my_key_*");
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        for (String k: keys) {
            System.out.println("---------- key = [" + k + "], val = [" +  ops.get(k) + "]");
        }
    }
}
