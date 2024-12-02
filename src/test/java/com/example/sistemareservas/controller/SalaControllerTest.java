package com.example.sistemareservas.controller;

import com.example.sistemareservas.Service.SalaService;
import com.example.sistemareservas.model.Sala;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
public class SalaControllerTest {

    @Mock
    private SalaService salaService;

    @InjectMocks
    private SalaController salaController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(salaController).build();
    }

    @Test
    public void testCriarSala() throws Exception {
        Sala sala = new Sala();
        sala.setNome("Sala 1");
        sala.setCapacidade(10);
        sala.setRecursos(List.of("Projetor"));

        when(salaService.criarSala(sala)).thenReturn(sala);

        mockMvc.perform(post("/salas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"nome\": \"Sala 1\", \"capacidade\": 10, \"recursos\": [\"Projetor\"] }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Sala 1"));
    }

    @Test
    public void testBuscarSalaPorId() throws Exception {
        Sala sala = new Sala();
        sala.setNome("Sala 1");

        when(salaService.buscarSalaPorId("1")).thenReturn(java.util.Optional.of(sala));

        mockMvc.perform(get("/salas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Sala 1"));
    }
}
