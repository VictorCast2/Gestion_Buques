package com.app.repository;

import com.app.collections.Factura.pojo.Proceso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcesoRepository extends MongoRepository<Proceso, String> {
}