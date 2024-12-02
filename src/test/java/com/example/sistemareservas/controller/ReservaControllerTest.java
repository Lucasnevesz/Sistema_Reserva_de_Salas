package com.example.sistemareservas.controller;

import com.example.sistemareservas.Service.ReservaService;
import com.example.sistemareservas.model.Reserva;
import com.example.sistemareservas.model.Sala;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootTest
public class ReservaControllerTest {

    @Mock
    private ReservaService reservaService;

    @InjectMocks
    private ReservaController reservaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reservaController).build();
    }

    @Test
    public void testCriarReserva() throws Exception {
        // Usando o Builder para criar uma instância de Sala
        Sala sala = new Sala.Builder()
            .id("1")
            .nome("Sala de Reunião")
            .capacidade(10)
            .recursos(Arrays.asList("Projetor", "Quadro Branco"))
            .status("A")
            .ativa(true)
            .build();
        
        Reserva reserva = new Reserva();
        reserva.setSala(sala);
        reserva.setDescricao("Reunião com equipe");
        reserva.setDataHora(LocalDateTime.parse("2024-12-10T09:00:00"));
        reserva.setDataHoraFim(LocalDateTime.parse("2024-12-10T10:00:00"));

        doNothing().when(reservaService).salvarReserva(reserva);

        mockMvc.perform(post("/reservas")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"descricao\": \"Reunião com equipe\", \"salaId\": \"1\", \"dataHora\": \"2024-12-10T09:00:00\", \"dataHoraFim\": \"2024-12-10T10:00:00\" }"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.descricao").value("Reunião com equipe"))
            .andExpect(jsonPath("$.salaId").value("1"));
    }

    @Test
    public void testBuscarReservaPorId() throws Exception {
        Sala sala = new Sala.Builder()
            .id("1")
            .nome("Sala de Reunião")
            .capacidade(10)
            .recursos(Arrays.asList("Projetor", "Quadro Branco"))
            .status("A")
            .ativa(true)
            .build();
        
        Reserva reserva = new Reserva();
        reserva.setDescricao("Reunião com equipe");
        reserva.setSala(sala);
        reserva.setDataHora(LocalDateTime.parse("2024-12-10T09:00:00"));
        reserva.setDataHoraFim(LocalDateTime.parse("2024-12-10T10:00:00"));

        when(reservaService.buscarReservaPorId("1")).thenReturn(reserva);

        mockMvc.perform(get("/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Reunião com equipe"))
                .andExpect(jsonPath("$.salaId").value("1"));
    }
}
