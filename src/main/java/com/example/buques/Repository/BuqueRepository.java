package com.example.buques.repository;

import com.example.buques.docs.Buque.Buque;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BuqueRepository extends MongoRepository<Buque, String> {
}
