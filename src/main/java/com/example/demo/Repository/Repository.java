package com.example.demo.Repository;

import com.example.demo.Model.Model;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface Repository extends MongoRepository<Model, String> {
    Optional<Model> findByUuid(String uuid);

    void deleteByUuid(String uuid);
}
