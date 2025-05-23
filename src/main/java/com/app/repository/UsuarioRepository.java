package com.app.repository;

import com.app.collections.Usuario.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    Set<Usuario> findAllByEmpresa_Nit(String nit);
    List<Usuario> findByEmpresaNotNull();
}