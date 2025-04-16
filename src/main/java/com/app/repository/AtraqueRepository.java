package com.app.repository;

import com.app.collections.Atraque.Atraque;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtraqueRepository extends MongoRepository<Atraque, String> {
}
