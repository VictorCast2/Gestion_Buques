package com.app.configuration.Redis;

import com.app.dto.request.AuthLoginRequest;
import lombok.Data;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

@Data
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