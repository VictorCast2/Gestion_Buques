package com.app.service;

import com.app.collections.Muelle.Enum.EEstado;
import com.app.collections.Muelle.Muelle;
import com.app.dto.request.MuelleRequest;
import com.app.dto.response.AuthResponse;
import com.app.repository.MuelleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MuelleService {

    @Autowired
    private MuelleRepository muelleRepository;

    public Muelle getMuelleById(String id) {
        return muelleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Error: el muelle con id " + id + " no existe o ha sido eliminado"));
    }

    public List<Muelle> getMuelles() {
        return muelleRepository.findAll();
    }

    public AuthResponse crearMuelle(@Valid MuelleRequest muelleRequest) {

        Muelle muelle = Muelle.builder()
                .nombre(muelleRequest.nombre())
                .capacidadBuques(muelleRequest.capacidadBuques())
                .capacidad(muelleRequest.capacidad())
                .estado(EEstado.valueOf(muelleRequest.estadoMuelle()))
                .build();

        muelleRepository.save(muelle);

        return new AuthResponse("Muelle creado exitosamente");
    }

    public AuthResponse deleteMuelleById(String id) {

        Muelle muelle = this.getMuelleById(id);

        muelleRepository.delete(muelle);

        return new AuthResponse("Muelle eliminado exitosamente");
    }
}
