package com.example.buques.AgenteNaviero.repository;

import com.example.buques.AgenteNaviero.entity.AgenteNaviero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Locale;
import java.util.Optional;

public interface AgenteRepository extends JpaRepository<AgenteNaviero, Long> {

    Optional<AgenteNaviero> findByNombre(String nombre);
    AgenteNaviero findByCorreo(String correo);
}
