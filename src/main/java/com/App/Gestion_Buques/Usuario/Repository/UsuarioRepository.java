package com.App.Gestion_Buques.Usuario.Repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.App.Gestion_Buques.Usuario.Entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    // Método para buscar un usuario por su email
    Optional<UsuarioEntity> findByEmail(String email);
}
