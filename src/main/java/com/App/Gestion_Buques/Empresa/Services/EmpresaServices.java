package com.App.Gestion_Buques.Empresa.Services;

import lombok.*;
import java.util.*;
import org.springframework.stereotype.Service;
import com.App.Gestion_Buques.Empresa.Entity.EmpresaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.App.Gestion_Buques.Empresa.Repository.EmpresaRepository;

@Data
@Service
@AllArgsConstructor
public class EmpresaServices {

    @Autowired
    private EmpresaRepository empresaRepository; // Repositorio de la entidad EmpresaEntity.

    /**
     * Método que permite crear una empresa.
     * @param empresa
     * @return
     */
    public EmpresaEntity crearEmpresa(EmpresaEntity empresa) {
        return empresaRepository.save(empresa);
    }

    /**
     * Método que permite modificar una empresa.
     * @param empresa
     */
    public void modificarEmpresa(EmpresaEntity empresa){
        if(empresaRepository.existsById(empresa.getId())) {
            empresaRepository.save(empresa);
        }
    }

    /**
     * Método que permite eliminar una empresa.
     * @param Id
     */
    public void eliminarEmpresa(Long Id) {
        empresaRepository.deleteById(Id);
    }

    /**
     * Método que permite encontrar una empresa por su ID.
     * @param Id
     * @return
     */
    public Optional<EmpresaEntity> encontrarEmpresaPorID(Long Id) {
        return empresaRepository.findById(Id);
    }

    /**
     * Método que permite encontrar todas las empresas.
     * @return
     */
    public List<EmpresaEntity> encontrarTodasEmpresa() {
        return empresaRepository.findAll();
    }

}