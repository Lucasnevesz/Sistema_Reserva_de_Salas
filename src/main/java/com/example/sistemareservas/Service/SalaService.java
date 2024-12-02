package com.example.sistemareservas.Service;

import com.example.sistemareservas.model.Sala;
import com.example.sistemareservas.Repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    public Sala criarSala(Sala sala) {
        return salaRepository.save(sala);
    }

    public List<Sala> listarSalas() {
        return salaRepository.findAll();
    }

    public List<Sala> filtrarSalas(String data, Integer capacidade, String recursos) {
        List<Sala> salas = salaRepository.findAll();

        if (capacidade != null) {
            salas = salas.stream()
                .filter(s -> s.getCapacidade() >= capacidade)
                .collect(Collectors.toList());
        }

        if (recursos != null && !recursos.isEmpty()) {
            String[] recursosArray = recursos.split(",");
            salas = salas.stream()
                .filter(s -> s.getRecursos().containsAll(List.of(recursosArray)))
                .collect(Collectors.toList());
        }

        return salas;
    }

    public Optional<Sala> buscarSalaPorId(String id) {
        return salaRepository.findById(id);
    }

    public Sala atualizarSala(Sala sala) {
        Optional<Sala> salaExistente = salaRepository.findById(sala.getId());
        if (salaExistente.isPresent()) {
            Sala atualizada = salaExistente.get();
            atualizada.setNome(sala.getNome());
            atualizada.setCapacidade(sala.getCapacidade());
            atualizada.setStatus(sala.getStatus());
            atualizada.setRecursos(sala.getRecursos());
            return salaRepository.save(atualizada);
        } else {
            throw new RuntimeException("Sala não encontrada para atualização");
        }
    }

    public void desativarSala(String id) {
        Optional<Sala> sala = salaRepository.findById(id);
        sala.ifPresent(s -> {
            s.setStatus("I");
            salaRepository.save(s);
        });
    }

    public boolean isSalaDesativada(String salaId) {
        Optional<Sala> sala = salaRepository.findById(salaId);
        return sala.map(Sala::getStatus).orElse("A").equals("I");
    }
}
