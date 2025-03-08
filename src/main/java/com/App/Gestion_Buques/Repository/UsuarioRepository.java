package com.App.Gestion_Buques.Repository;

import com.App.Gestion_Buques.Entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    // Método para buscar un usuario por su nombre de usuario
    Optional<UsuarioEntity> findByUsername(String username);
    // Método para buscar un usuario por su email
    Optional<UsuarioEntity> findByEmail(String email);
}
