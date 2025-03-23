package com.example.buques.repository;

import com.example.buques.docs.Proceso.Proceso;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProcesoRepository extends MongoRepository<Proceso, String> {
}
