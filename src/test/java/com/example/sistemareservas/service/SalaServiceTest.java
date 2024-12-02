package com.example.sistemareservas.service;

import com.example.sistemareservas.model.Sala;
import com.example.sistemareservas.Repository.SalaRepository;
import com.example.sistemareservas.Service.SalaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SalaServiceTest {

    @SuppressWarnings("removal")
    @MockBean
    private SalaRepository salaRepository;

    @Autowired
    private SalaService salaService;

    private Sala sala;

    @BeforeEach
    public void setUp() {
        sala = new Sala();
        sala.setNome("Sala de Reunião 1");
        sala.setCapacidade(10);
        sala.setRecursos(List.of("Projetor", "TV"));
        sala.setStatus("A");
    }

    @Test
    public void testCriarSala() {
        when(salaRepository.save(sala)).thenReturn(sala);

        Sala salaSalva = salaService.criarSala(sala);

        assertNotNull(salaSalva);
        assertEquals("Sala de Reunião 1", salaSalva.getNome());
        verify(salaRepository, times(1)).save(sala);
    }

    @Test
    public void testBuscarSalaPorId() {
        when(salaRepository.findById("1")).thenReturn(Optional.of(sala));

        Optional<Sala> salaBuscada = salaService.buscarSalaPorId("1");

        assertTrue(salaBuscada.isPresent());
        assertEquals("Sala de Reunião 1", salaBuscada.get().getNome());
    }

    @Test
    public void testSalaNaoEncontrada() {
        when(salaRepository.findById("99")).thenReturn(Optional.empty());

        Optional<Sala> salaBuscada = salaService.buscarSalaPorId("99");

        assertFalse(salaBuscada.isPresent());
    }
}
