package com.app.repository;

import com.app.collections.Atraque.Atraque;
import com.app.collections.Atraque.Enum.EResultado;
import com.app.collections.Usuario.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtraqueRepository extends MongoRepository<Atraque, String> {
    List<Atraque> findByAgenteNaviero(Usuario usuario);
    List<Atraque> findByEstadoSolicitud(EResultado estadoSolicitud);

    int countByMuelleIdAndEstadoSolicitud(String id, EResultado eResultado);
}