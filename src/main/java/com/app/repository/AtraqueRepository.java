package com.app.repository;

import com.app.collections.Atraque.Atraque;
import com.app.collections.Atraque.Enum.EResultado;
import com.app.collections.Usuario.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtraqueRepository extends MongoRepository<Atraque, String> {
    List<Atraque> findByAgenteNaviero(Usuario usuario);

    List<Atraque> findByEstadoSolicitud(EResultado estadoSolicitud);

    int countByMuelleIdAndEstadoSolicitud(String id, EResultado eResultado);

    Optional<Atraque> findTopByAgenteNavieroAndEstadoSolicitudOrderByFechaAprobacionDesc(Usuario agenteNaviero, EResultado eResultado);
}