package com.simulacao.AlunosVsFaculdade.service;

import com.simulacao.AlunosVsFaculdade.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimuladorService {

    private Thread simulacaoThread;
    private Simulador simulador;

    public synchronized void iniciarSimulacao(int inteligencia, int dificuldade, int velocidade) {
        if (simulacaoThread != null && simulacaoThread.isAlive()) {
            simulador.interromper();
            try {
                simulacaoThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Tabuleiro.agentes.clear();
        Tabuleiro.iniciaTabuleiro();

        Aluno aluno1 = new Aluno("Maria", 2, 3, inteligencia);
        Aluno aluno2 = new Aluno("João", 5, 6, inteligencia);

        simulador = new Simulador();
        simulador.adicionarAluno(aluno1);
        simulador.adicionarAluno(aluno2);

        Tabuleiro.adicionaAgente(aluno1);
        Tabuleiro.adicionaAgente(aluno2);

        simulacaoThread = new Thread(() -> simulador.comecarSimulacao(dificuldade, velocidade));
        simulacaoThread.start();
    }

    public String[][] getMapaAtual() {
        return Tabuleiro.mapa;
    }

    public List<String> getResultado() {
        if (simulador == null) {
            return List.of("A simulação ainda não foi iniciada.");
        }
        return simulador.mostrarRelatorioAlunos();
    }

    public String getPosResultado() {
        if (simulador == null) {
            return "A simulação ainda não foi iniciada.";
        }
        return simulador.mostrarQuantidadeAlunosReprovados();
    }

    public int getRodadaAtual() {
        if (simulador == null) {
            return 0;
        }

        return simulador.rodadaAtual();
    }
}
