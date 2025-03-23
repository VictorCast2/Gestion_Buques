package com.example.buques.repository.Buque;

import com.example.buques.docs.Buque.Buque;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuqueRepository extends MongoRepository<Buque, String> {
}
