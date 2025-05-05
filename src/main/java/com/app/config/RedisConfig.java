package com.app.config;

import com.app.dto.request.AuthLoginRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, AuthLoginRequest> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, AuthLoginRequest> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // Serializador para claves
        template.setKeySerializer(new StringRedisSerializer());
        // Serializador para valores (usa JSON)
        Jackson2JsonRedisSerializer<AuthLoginRequest> valueSerializer =
                new Jackson2JsonRedisSerializer<>(AuthLoginRequest.class);
        template.setValueSerializer(valueSerializer);
        return template;
    }

}