package com.example.sistemareservas.service;

import com.example.sistemareservas.Repository.ReservaRepository;
import com.example.sistemareservas.Service.SalaService;
import com.example.sistemareservas.Service.ReservaService;
import com.example.sistemareservas.model.Reserva;
import com.example.sistemareservas.model.Sala;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ReservaServiceTest {

    @SuppressWarnings("removal")
    @MockBean
    private ReservaRepository reservaRepository;

    @SuppressWarnings("removal")
    @MockBean
    private SalaService salaService;

    @Autowired
    private ReservaService reservaService;

    private Sala sala;
    private Reserva reserva;

    @BeforeEach
    public void setUp() {
        sala = new Sala();
        sala.setNome("Sala de Reunião 1");
        sala.setCapacidade(10);
        sala.setRecursos(List.of("Projetor", "TV"));
        sala.setStatus("A");

        reserva = new Reserva();
        reserva.setDescricao("Reunião com equipe");
        reserva.setSala(sala);
        reserva.setDataHora(LocalDateTime.parse("2024-12-10T09:00:00"));
        reserva.setDataHoraFim(LocalDateTime.parse("2024-12-10T10:00:00"));
    }

    @Test
    public void testCriarReserva() {
        when(salaService.buscarSalaPorId("1")).thenReturn(java.util.Optional.of(sala));
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        reservaService.salvarReserva(reserva);

        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    public void testReservaSalaNaoDisponivel() {
        when(salaService.buscarSalaPorId("1")).thenReturn(java.util.Optional.of(sala));
        when(reservaRepository.findBySalaAndDataHora(sala, LocalDateTime.parse("2024-12-10T09:00:00"))).thenReturn(reserva);

        assertThrows(IllegalStateException.class, () -> {
            reservaService.salvarReserva(reserva);
        });
    }
}
