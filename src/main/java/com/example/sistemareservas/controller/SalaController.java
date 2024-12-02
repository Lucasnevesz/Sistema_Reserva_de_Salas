package com.example.sistemareservas.controller;

import com.example.sistemareservas.model.Sala;
import com.example.sistemareservas.Service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @GetMapping
    public String listarSalas(
            @RequestParam(required = false) String data,
            @RequestParam(required = false) Integer capacidade,
            @RequestParam(required = false) String recursos,
            Model model
    ) {
        List<Sala> salas = salaService.filtrarSalas(data, capacidade, recursos);
        model.addAttribute("salas", salas);
        return "salas";
    }

    @PostMapping("/criar")
    public String criarSala(
            @RequestParam String nome,
            @RequestParam int capacidade,
            @RequestParam(required = false) String recursos,
            @RequestParam(defaultValue = "A") String status
    ) {
        Sala sala = new Sala.Builder()
                .nome(nome)
                .capacidade(capacidade)
                .status(status)
                .recursos(recursos != null && !recursos.isEmpty() ? List.of(recursos.split(",")) : List.of())
                .build();
        salaService.criarSala(sala);
        return "redirect:/salas";
    }

    @GetMapping("/{id}")
    public String buscarSala(@PathVariable String id, Model model) {
        Optional<Sala> sala = salaService.buscarSalaPorId(id);
        sala.ifPresent(s -> model.addAttribute("sala", s));
        return "salaDetalhes";
    }

    @GetMapping("/{id}/editar")
    public String exibirFormularioEdicao(@PathVariable String id, Model model) {
        Optional<Sala> sala = salaService.buscarSalaPorId(id);
        if (sala.isPresent()) {
            Sala salaObj = sala.get();
            String recursos = String.join(", ", salaObj.getRecursos());
            model.addAttribute("recursosString", recursos);
            model.addAttribute("sala", salaObj);
            return "editarSala";
        }
        return "redirect:/salas?erro=SalaNaoEncontrada";
    }

    @PostMapping("/{id}/editar")
    public String salvarEdicao(
            @PathVariable String id,
            @RequestParam String nome,
            @RequestParam int capacidade,
            @RequestParam(required = false) String recursos,
            @RequestParam(defaultValue = "A") String status
    ) {
        Optional<Sala> salaExistente = salaService.buscarSalaPorId(id);
        if (salaExistente.isPresent()) {
            Sala salaAtualizada = salaExistente.get();
            salaAtualizada.setNome(nome);
            salaAtualizada.setCapacidade(capacidade);
            salaAtualizada.setStatus(status);

            if (recursos != null && !recursos.isEmpty()) {
                salaAtualizada.setRecursos(List.of(recursos.split(",")));
            }
            salaService.atualizarSala(salaAtualizada);
        }
        return "redirect:/salas";
    }

    @PutMapping("/desativar/{id}")
    public String desativarSala(@PathVariable String id) {
        salaService.desativarSala(id);
        return "redirect:/salas";
    }
}
