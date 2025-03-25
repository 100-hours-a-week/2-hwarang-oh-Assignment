package com.ktb.community.external.redis;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String getKey(Long userId) {
        return "refresh:" + userId;
    }

    public void save(Long userId, String refreshToken, Long expiration) {
        redisTemplate.opsForValue().set(getKey(userId), refreshToken, expiration, TimeUnit.MILLISECONDS);
    }

    public Optional<String> get(Long userId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(getKey(userId)));
    }

    public void delete(Long userId) {
        redisTemplate.delete(getKey(userId));
    }

    public boolean exists(Long userId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(getKey(userId)));
    }
}
