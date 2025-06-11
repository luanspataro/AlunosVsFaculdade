package com.simulacao.AlunosVsFaculdade.controller;

import com.simulacao.AlunosVsFaculdade.service.SimuladorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SimuladorController {

    @Autowired
    private SimuladorService simuladorService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/iniciar")
    public String iniciarSimulacao(@RequestParam int inteligencia, @RequestParam int dificuldade, @RequestParam int velocidade) {
        simuladorService.iniciarSimulacao(inteligencia, dificuldade, velocidade);
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/mapa")
    public String[][] getMapa() {
        return simuladorService.getMapaAtual();
    }

    @ResponseBody
    @GetMapping("/resultado")
    public String getResultado() {
        List<String> linhas = simuladorService.getResultado();
        return String.join("<br>", linhas);
    }

    @ResponseBody
    @GetMapping("/posresultado")
    public String getPosResultados() {
        return  simuladorService.getPosResultado();
    }

    @ResponseBody
    @GetMapping("/rodada_atual")
    public int getRodadaAtual() {
        return  simuladorService.getRodadaAtual();
    }

}
