package com.simulacao.AlunosVsFaculdade.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class Simulador {
    private int contadorRodadas = 1;
    private int quantidadeTotalDeRodadas = 0;
    private int maxRodadas = 100;
    private Set<Professor> professores;
    private Set<Aluno> alunos;
    private volatile boolean interrompido = false;

    public Simulador() {
        this.professores = new HashSet<>();
        this.alunos = new HashSet<>();
    }

    public void interromper() {
        this.interrompido = true;
    }

    public void comecarSimulacao(int dificuldade, int velocidade) {
        long ultimaProva   = System.currentTimeMillis();
        long intervaloProva = 0;

        if (dificuldade == 1) {
            int [] posicaoProfessor = Tabuleiro.geraPosicaoAleatoria();
            Professor prof1 = new Professor(posicaoProfessor[0], posicaoProfessor[1], 2, 1);
            adicionarProfessor(prof1);
            Tabuleiro.adicionaAgente(prof1);
            intervaloProva = 1000;
        }

        while (this.contadorRodadas <= maxRodadas && !interrompido) {
            if (System.currentTimeMillis() - ultimaProva > intervaloProva) {
                Tabuleiro.apareceProva();
                ultimaProva = System.currentTimeMillis();
            }

            Tabuleiro.mostraTabuleiro();
            Tabuleiro.aguardaRodada(velocidade);

            for (Aluno aluno : alunos) {
                if (!aluno.estaAprovado() && !aluno.estaReprovado()) {
                    aluno.mover();
                    for (Professor prof : professores) {
                        if (aluno.x == prof.x && aluno.y == prof.y) {
                            aluno.encontrarProfessor(prof.getForca());
                            if (aluno.estaReprovado()) break;
                        }
                    }
                }
            }

            for (Professor prof : professores) {
                Agente.andaAleatorio(prof);
            }
            rodadaAtual();
        }

        avaliarAlunos();

        mostrarQuantidadeAlunosAprovados();
        mostrarQuantidadeAlunosReprovados();
        mostrarRelatorioAlunos();
    }

    private void avaliarAlunos() {
        for (Aluno aluno : alunos) {
            if (!aluno.estaAprovado() && !aluno.estaReprovado()) {
                aluno.setReprovado(true);
                aluno.setMotivoReprovacao("nota_insuficiente");
            }
        }
    }

    public void rodadaAtual() {
        System.out.println("Rodada atual: " + contadorRodadas);
        contadorRodadas++;
        quantidadeTotalDeRodadas++;
    }

    public Set<Aluno> getAlunos() {
        return Collections.unmodifiableSet(alunos);
    }

    public Set<Professor> getProfessores() {
        return Collections.unmodifiableSet(professores);
    }

    public void adicionarProfessor(Professor professor) {
        if (professor != null) {
            professores.add(professor);
        }
    }

    public void adicionarAluno(Aluno aluno) {
        if (aluno != null) {
            alunos.add(aluno);
        }
    }

    public String mostrarQuantidadeAlunosReprovados() {
        if (alunos == null) {
            return "Escolha a dificuldade para começar.";
        }

        int totalProfessor = 0;
        int totalNotaInsuficiente = 0;

        for (Aluno aluno : alunos) {
            if (aluno.estaReprovado()) {
                if ("professor".equals(aluno.getMotivoReprovacao())) {
                    totalProfessor++;
                } else if ("nota_insuficiente".equals(aluno.getMotivoReprovacao())) {
                    totalNotaInsuficiente++;
                }
            }
        }

        return ("Alunos reprovados pelo professor: " + totalProfessor + "<br> Alunos reprovados por não ter atingido a nota: " + totalNotaInsuficiente);
    }

    public void mostrarQuantidadeAlunosAprovados() {
        int total = 0;
        for (Aluno aluno : alunos) {
            if (aluno.estaAprovado()) {
                total++;
            }
        }
        System.out.println("Alunos aprovados: " + total);
    }

    public List<String> mostrarRelatorioAlunos() {
        if (alunos == null) {
            List<String> resultado = new ArrayList<>();
            resultado.add("A simulação ainda não foi iniciada.");
            return resultado;
        }

        List<String> resultado = new ArrayList<>();
        for (Aluno aluno : alunos) {
            String status = "";

            if (aluno.estaAprovado()) {
                status = "✅ Aprovado";
            } else if (aluno.estaReprovado()) {
                if ("professor".equals(aluno.getMotivoReprovacao())) {
                    status = "❌ Reprovado (encontrou o professor)";
                } else if ("nota_insuficiente".equals(aluno.getMotivoReprovacao())) {
                    status = "❌ Reprovado (nota insuficiente)";
                }
            }


            resultado.add(aluno.getNome() + " na posição (" + aluno.x + "," + aluno.y + ") tem "
                    + aluno.getNotas() + " pontos no total. Perdeu "
                    + aluno.getNotasPerdidas() + " pontos com os professores. " + status);
        }

        return resultado;
    }

    public void alterarMaxRodadas(int maxRodadas) {
        this.maxRodadas = maxRodadas;
    }
}