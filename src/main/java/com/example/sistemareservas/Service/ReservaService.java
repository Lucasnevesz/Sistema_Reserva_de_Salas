package com.example.sistemareservas.Service;

import com.example.sistemareservas.model.Reserva;
import com.example.sistemareservas.model.Sala;
import com.example.sistemareservas.Repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public Reserva criarReservaCompleta(Sala sala, LocalDateTime dataInicio, LocalDateTime dataFim, String usuario, String descricao) {
        Reserva reserva = new Reserva();
        reserva.setSala(sala);
        reserva.setUsuario(usuario);
        reserva.setDataHora(dataInicio);
        reserva.setDataHoraFim(dataFim);
        reserva.setDescricao(descricao);
        reserva.setStatus("Ativa");
        return reserva;
    }

    public Reserva criarReservaComIdEDescricao(String id, String descricao, LocalDateTime dataInicio) {
        Reserva reserva = new Reserva();
        reserva.setDescricao(descricao);
        reserva.setDataHora(dataInicio);
        return reserva;
    }

    public Reserva criarReservaSimples(String usuario, Sala sala, LocalDateTime dataInicio) {
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setSala(sala);
        reserva.setDataHora(dataInicio);
        reserva.setStatus("Ativa");
        return reserva;
    }

    public void salvarReserva(Reserva reserva) {
        reservaRepository.save(reserva);
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public boolean verificarConflito(String salaId, LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<Reserva> reservasExistentes = reservaRepository.findBySalaId(salaId);

        for (Reserva reserva : reservasExistentes) {
            if ((dataInicio.isBefore(reserva.getDataHoraFim()) && dataFim.isAfter(reserva.getDataHora())) ||
                (dataInicio.isEqual(reserva.getDataHora()) || dataFim.isEqual(reserva.getDataHoraFim()))) {
                return true;
            }
        }
        return false;
    }

    public Reserva buscarReservaPorId(String id) {
        return reservaRepository.findById(id).orElse(null);
    }

    public void cancelarReserva(String id) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(id);
        reservaOpt.ifPresent(reservaRepository::delete);
    }
}
