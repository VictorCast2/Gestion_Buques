package com.example.buques.repository.Proceso;

import com.example.buques.docs.Proceso.Proceso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcesoRepository extends MongoRepository<Proceso, String> {
}
