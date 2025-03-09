package com.App.Gestion_Buques.Empresa.Repository;

import org.springframework.stereotype.Repository;
import com.App.Gestion_Buques.Empresa.Entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long>{
}