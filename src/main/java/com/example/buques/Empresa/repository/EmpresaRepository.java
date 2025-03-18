package com.example.buques.Empresa.repository;

import com.example.buques.Empresa.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByNit(String nit);
    Optional<Empresa> findByNombre(String nombre);
}
