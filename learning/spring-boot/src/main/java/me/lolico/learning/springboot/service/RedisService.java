package me.lolico.learning.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author lolico
 */
@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper objectMapper;


    public RedisService(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }
}
