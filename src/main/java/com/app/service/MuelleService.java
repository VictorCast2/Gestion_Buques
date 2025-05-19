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

    public MuelleRequest getMuelleRequestById(String id) {

        Muelle muelle = this.getMuelleById(id);

        return new MuelleRequest(
                muelle.getNombre(),
                muelle.getCapacidadBuques(),
                muelle.getCapacidad(),
                muelle.getEstado().toString()
        );
    }

    public List<Muelle> getMuelles() {
        return muelleRepository.findAll();
    }

    /**
     * Método para obtener los muelles con esto 'DISPONIBLE'
     * @return una lista de muelle con el estado 'DISPONIBLE'
     */
    public List<Muelle> getMuelleByEstadoDisponible() {
        return muelleRepository.findByEstado(EEstado.DISPONIBLE);
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

    public AuthResponse updateMuelle(@Valid MuelleRequest muelleRequest, String id) {

        Muelle muelleActualizado = this.getMuelleById(id);

        muelleActualizado.setNombre(muelleRequest.nombre());
        muelleActualizado.setCapacidadBuques(muelleRequest.capacidadBuques());
        muelleActualizado.setCapacidad(muelleRequest.capacidad());
        muelleActualizado.setEstado(EEstado.valueOf(muelleRequest.estadoMuelle()));

        muelleRepository.save(muelleActualizado);

        return new AuthResponse("Muelle actualizado exitosamente");
    }

    public AuthResponse deleteMuelleById(String id) {

        Muelle muelle = this.getMuelleById(id);

        muelleRepository.delete(muelle);

        return new AuthResponse("Muelle eliminado exitosamente");
    }
}
