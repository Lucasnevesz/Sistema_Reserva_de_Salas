package com.example.sistemareservas.Repository;

import com.example.sistemareservas.model.Sala;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SalaRepository extends MongoRepository<Sala, String> {
}
