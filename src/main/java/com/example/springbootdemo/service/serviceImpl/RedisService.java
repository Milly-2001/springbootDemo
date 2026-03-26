package com.example.springbootdemo.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 存储字符串
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 存储并设置过期时间
    public void setWithExpire(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    // 获取值
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 删除 key
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    // 设置过期时间
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    // 递增（适用于计数器）
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    // 哈希操作
    public void hset(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object hget(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    // 列表操作（左推）
    public void lpush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    // 右弹出
    public Object rpop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    // 集合操作
    public void sadd(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    // 有序集合操作
    public void zadd(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }
}
