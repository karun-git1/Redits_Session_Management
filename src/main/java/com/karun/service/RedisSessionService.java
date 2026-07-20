package com.karun.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisSessionService {

    private static final String SESSION_PREFIX = "session:";
    private static final long SESSION_TIMEOUT = 30; // minutes

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Store session in Redis
     */
    public void createSession(String token, Long userId) {

        String key = SESSION_PREFIX + token;

        redisTemplate.opsForValue().set(
                key,
                userId,
                Duration.ofMinutes(SESSION_TIMEOUT));

    }

    /**
     * Get user id from Redis
     */
    public Long getUserId(String token) {

        String key = SESSION_PREFIX + token;

        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return null;
        }

        return Long.valueOf(value.toString());

    }

    /**
     * Delete session
     */
    public void deleteSession(String token) {

        redisTemplate.delete(SESSION_PREFIX + token);

    }

    /**
     * Check session exists
     */
    public boolean isSessionValid(String token) {

        return Boolean.TRUE.equals(
                redisTemplate.hasKey(SESSION_PREFIX + token));

    }

}