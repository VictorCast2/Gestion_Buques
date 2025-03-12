package com.App.GestionBuques.AgenteNav.Repository;

import com.App.GestionBuques.AgenteNav.Model.AgenteNavEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AgenteNavRepository extends JpaRepository<AgenteNavEntity, Long> {
    // Método para buscar un usuario por su email
    Optional<AgenteNavEntity> findByEmail(String email);
}