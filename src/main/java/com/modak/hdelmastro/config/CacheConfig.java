package com.modak.hdelmastro.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class CacheConfig {


    @Value("${cache.caffeine.spec}")
    private String caffeineSpec;

    @Bean
    public Cache<String, List<LocalDateTime>> caffeineCache() {
        return Caffeine.from(caffeineSpec).build();
    }
}
