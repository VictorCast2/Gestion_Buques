package com.app.repository;

import com.app.collections.Muelle.Enum.EEstado;
import com.app.collections.Muelle.Muelle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MuelleRepository extends MongoRepository<Muelle, String> {
    List<Muelle> findByEstado(EEstado eEstado);
}
