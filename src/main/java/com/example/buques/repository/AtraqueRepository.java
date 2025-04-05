package com.example.buques.repository;

import com.example.buques.docs.SolicitudAtraque.SolicitudAtraque;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtraqueRepository extends MongoRepository<SolicitudAtraque, String> {
}
