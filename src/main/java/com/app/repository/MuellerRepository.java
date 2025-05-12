package com.app.repository;

import com.app.collections.Muelle.Muelle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuellerRepository extends MongoRepository<Muelle, String> {
}
