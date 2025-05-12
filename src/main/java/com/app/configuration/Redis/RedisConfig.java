package com.app.configuration.Redis;

import com.app.collections.Usuario.pojo.TwoFactorEnabledRequest;
import com.app.dto.request.AuthLoginRequest;
import lombok.Data;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

@Data
@Configuration
public class RedisConfig {

    @Bean(name = "authLoginRequestRedisTemplate")
    public RedisTemplate<String, AuthLoginRequest> authLoginRequestRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, AuthLoginRequest> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<AuthLoginRequest> valueSerializer =
                new Jackson2JsonRedisSerializer<>(AuthLoginRequest.class);
        template.setValueSerializer(valueSerializer);
        return template;
    }

    @Bean(name = "twoFactorEnabledRequestRedisTemplate")
    public RedisTemplate<String, TwoFactorEnabledRequest> twoFactorEnabledRequestRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, TwoFactorEnabledRequest> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<TwoFactorEnabledRequest> valueSerializer =
                new Jackson2JsonRedisSerializer<>(TwoFactorEnabledRequest.class);
        template.setValueSerializer(valueSerializer);
        return template;
    }

}