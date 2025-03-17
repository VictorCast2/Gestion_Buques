package com.example.buques.AgenteNaviero.service;

import com.example.buques.AgenteNaviero.entity.AgenteNaviero;
import com.example.buques.AgenteNaviero.repository.AgenteRepository;
import com.example.buques.Empresa.repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgenteService {

    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<AgenteNaviero> getAgentesNavieros() {
        return agenteRepository.findAll();
    }

    public AgenteNaviero addAgenteNaviero(AgenteNaviero agenteNaviero) {
        return agenteRepository.save(agenteNaviero);
    }

    public AgenteNaviero updateAgenteNaviero(Long id, AgenteNaviero agenteNavieroActualizado) {
        AgenteNaviero agenteNaviero = agenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El agente con el id " + id + " no existe o ha sido eliminado"));

        agenteNaviero.setCedula(agenteNaviero.getCedula());
        agenteNaviero.setNombre(agenteNaviero.getNombre());
        agenteNaviero.setApellido(agenteNaviero.getApellido());
        agenteNaviero.setTelefono(agenteNaviero.getTelefono());
        agenteNaviero.setCorreo(agenteNaviero.getCorreo());
        agenteNaviero.setPassword(agenteNaviero.getPassword());

        return agenteRepository.save(agenteNaviero);
    }

    public void deleteAgenteNaviero(Long id) {
        AgenteNaviero agenteNaviero = agenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El agente naviero con el id " + id + "no esxiste o ha sido eliminado"));
    }

    public AgenteNaviero validarCredencialesAgenteNaviero(String correo, String password) {
        AgenteNaviero agenteNaviero = agenteRepository.findByCorreo(correo);

        if (agenteNaviero != null && agenteNaviero.getPassword().equals(password)) {
            return agenteNaviero;
        }
        return null;
    }
}
