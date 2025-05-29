package com.simulacao.AlunosVsFaculdade.service;

import com.simulacao.AlunosVsFaculdade.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimuladorService {

    private Simulador simulador;

    public void iniciarSimulacao(int inteligencia, int dificuldade) {
        Tabuleiro.agentes.clear();
        Tabuleiro.iniciaTabuleiro();

        Aluno aluno1 = new Aluno("Maria", 2, 3, inteligencia);
        Aluno aluno2 = new Aluno("João", 5, 6, inteligencia);

        simulador = new Simulador();
        simulador.adicionarAluno(aluno1);
        simulador.adicionarAluno(aluno2);

        Tabuleiro.adicionaAgente(aluno1);
        Tabuleiro.adicionaAgente(aluno2);

        new Thread(() -> simulador.comecarSimulacao(dificuldade)).start();
    }

    public String[][] getMapaAtual() {
        return Tabuleiro.mapa;
    }

    public List<String> getResultado() {
        return Simulador.mostrarRelatorioAlunos();
    }

    public String getPosResultado() {
        return Simulador.mostrarQuantidadeAlunosReprovados();
    }
}
