package com.example.buques.repository;

import com.example.buques.docs.Empresa.Empresa;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmpresaRepository extends MongoRepository<Empresa, String> {
}
