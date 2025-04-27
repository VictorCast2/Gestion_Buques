package com.app.repository;

import com.app.collections.Usuario.UsuarioRedis;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableRedisRepositories
public interface RedisUsuarioRepository extends CrudRepository<UsuarioRedis, String> {
}