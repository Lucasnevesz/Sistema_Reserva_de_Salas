package com.example.sistemareservas.Repository;

import com.example.sistemareservas.model.Reserva;
import com.example.sistemareservas.model.Sala;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends MongoRepository<Reserva, String> {
    Reserva findBySalaAndDataHora(Sala sala, LocalDateTime dataHora);
    List<Reserva> findBySalaId(String salaId);
}
