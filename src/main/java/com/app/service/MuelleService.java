package com.app.service;

import com.app.collections.Muelle.Enum.EEstado;
import com.app.collections.Muelle.Muelle;
import com.app.dto.request.MuelleRequest;
import com.app.dto.response.AuthResponse;
import com.app.repository.MuellerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuelleService {

    @Autowired
    private MuellerRepository muellerRepository;

    public List<Muelle> getMuelles() {
        return muellerRepository.findAll();
    }

    public AuthResponse crearMuelle(@Valid MuelleRequest muelleRequest) {

        Muelle muelle = Muelle.builder()
                .nombre(muelleRequest.nombre())
                .capacidadBuques(muelleRequest.capacidadBuques())
                .capacidad(muelleRequest.capacidad())
                .estado(EEstado.valueOf(muelleRequest.estadoMuelle()))
                .build();

        muellerRepository.save(muelle);

        return new AuthResponse("Muelle creado exitosamente");
    }
}
