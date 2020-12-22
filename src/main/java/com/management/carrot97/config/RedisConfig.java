package com.management.carrot97.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheConfiguration myRedisCacheConfiguration() {
//        Jackson2JsonRedisSerializer<Activity> serializer = new Jackson2JsonRedisSerializer<>(Activity.class);
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(serializer);
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair)
                .entryTtl(Duration.ofHours(1))
                .disableCachingNullValues();
//                .computePrefixWith(cacheName -> "redis");
        return cacheConfig;
    }

    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return method.getName() + "[" + Arrays.asList(params).toString() + "]";
            }
        };
    }

    @Primary
    @Bean
    public RedisCacheManager myRedisManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(myRedisCacheConfiguration());
        RedisCacheManager manager = builder.build();
        return manager;
    }

}
