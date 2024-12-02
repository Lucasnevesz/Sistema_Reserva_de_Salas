package com.example.sistemareservas.controller;

import com.example.sistemareservas.model.Reserva;
import com.example.sistemareservas.model.Sala;
import com.example.sistemareservas.Service.ReservaService;
import com.example.sistemareservas.Service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private SalaService salaService;

    @GetMapping("/minhas-reservas")
    public String minhasReservas(Model model) {
        List<Reserva> reservas = reservaService.listarReservas();
        List<Sala> salas = salaService.listarSalas();
        model.addAttribute("reservas", reservas);
        model.addAttribute("salas", salas);
        return "reservas";
    }

    @PostMapping("/criar")
    public String criarReserva(@RequestParam String salaId, 
                               @RequestParam String dataHoraInicio, 
                               @RequestParam String dataHoraFim, 
                               @RequestParam String usuario,
                               @RequestParam(required = false) String descricao) {
        try {
            LocalDateTime dataInicio = LocalDateTime.parse(dataHoraInicio);
            LocalDateTime dataFim = LocalDateTime.parse(dataHoraFim);

            Optional<Sala> salaOpt = salaService.buscarSalaPorId(salaId);
            if (salaOpt.isEmpty() || salaService.isSalaDesativada(salaId)) {
                return "errorInativo";
            }

            boolean conflito = reservaService.verificarConflito(salaId, dataInicio, dataFim);
            if (conflito) {
                return "error";
            }

            Reserva reserva = reservaService.criarReservaCompleta(
                salaOpt.get(), 
                dataInicio, 
                dataFim, 
                usuario, 
                descricao
            );
            reservaService.salvarReserva(reserva);

            return "redirect:/reservas/minhas-reservas";
        } catch (Exception e) {
            return "error";
        }
    }

    @DeleteMapping("/cancelar/{id}")
    public String cancelarReserva(@PathVariable String id) {
        reservaService.cancelarReserva(id);
        return "redirect:/reservas/minhas-reservas";
    }
}
