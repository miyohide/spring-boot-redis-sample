package com.github.miyohide.springbootredissample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MyService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final Logger log = LoggerFactory.getLogger(MyService.class);

    @Value("${app.records.num:500}")
    private int RECORDS_NUM;

    public MyService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setData() {
        log.info("start setData method");
        for (int i = 0; i < RECORDS_NUM; i++) {
            String k = String.format("my_key_%05d", i);
            String v = String.format("my_val_%05d", i);
            log.info("set data to redis. key = [" + k + "] v = [" + v + "]");
            redisTemplate.opsForValue().set(k, v);
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("end setData method");
    }

    public void getData() {
        log.info("start getData method");
        Set<String> keys = redisTemplate.keys("my_key_*");
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        for (String k: keys) {
            log.info("key = [" + k + "], val = [" +  ops.get(k) + "]");
        }
        log.info("end getData method");
    }
}
