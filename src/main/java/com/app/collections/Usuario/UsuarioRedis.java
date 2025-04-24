package com.app.collections.Usuario;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("UsuarioRedis")
public class UsuarioRedis {
    @Id
    private String correo;
    private String password;
}