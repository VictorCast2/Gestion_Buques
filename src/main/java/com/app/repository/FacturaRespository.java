package com.app.repository;

import com.app.collections.Factura.Factura;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRespository extends MongoRepository<Factura, String> {
}
