package com.app.repository;

import com.app.collections.Factura.Enum.EEstadoProceso;
import com.app.collections.Factura.Factura;
import com.app.collections.Usuario.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRespository extends MongoRepository<Factura, String> {
    List<Factura> findByAgenteNaviero(Usuario agenteNaviero);

    List<Factura> findByProcesosEstadoProceso(EEstadoProceso eEstadoProceso);
}