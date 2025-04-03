package com.example.buques.repository;

import com.example.buques.docs.Inspeccion.Inspeccion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspeccionRepository extends MongoRepository<Inspeccion, String> {
}
