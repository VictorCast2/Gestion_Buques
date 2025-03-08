package com.App.Gestion_Buques.Repository;

import com.App.Gestion_Buques.Entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>, CrudRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByUsername(String username);
}
