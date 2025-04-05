package com.example.buques.repository;

import com.example.buques.docs.Muelle.Muelle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuelleRepository extends MongoRepository<Muelle, String> {
}
