package com.App.Gestion_Buques.AgenteNav.Repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.App.Gestion_Buques.AgenteNav.Entity.AgenteNavEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AgenteNavRepository extends JpaRepository<AgenteNavEntity, Long> {
    // Método para buscar un usuario por su email
    Optional<AgenteNavEntity> findByEmail(String email);
}
