package com.simulacao.AlunosVsFaculdade.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class Simulador {
    private int contadorRodadas = 0;
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

        // Substitua os blocos de dificuldade por este:
        if (dificuldade == 1) {
            int[] pos = Tabuleiro.geraPosicaoAleatoria();
            Professor prof1 = new Professor(pos[0], pos[1], 2, 1);
            adicionarProfessor(prof1);
            Tabuleiro.adicionaAgente(prof1);
            intervaloProva = velocidade * 2L + velocidade / 2;
        }

        if (dificuldade == 2) {
            for (int i = 0; i < 3; i++) {
                int[] pos = Tabuleiro.geraPosicaoAleatoria();
                Professor prof = new Professor(pos[0], pos[1], 2, 1);
                adicionarProfessor(prof);
                Tabuleiro.adicionaAgente(prof);
            }
            intervaloProva = velocidade * 2L + velocidade / 2;
        }

        if (dificuldade == 3) {
            for (int i = 0; i < 4; i++) {
                int[] pos = Tabuleiro.geraPosicaoAleatoria();
                Professor prof = new Professor(pos[0], pos[1], 2, 1);
                adicionarProfessor(prof);
                Tabuleiro.adicionaAgente(prof);
            }
            intervaloProva = velocidade * 2L + velocidade / 2;
        }

        while (this.contadorRodadas < maxRodadas && !interrompido) {
            if (System.currentTimeMillis() - ultimaProva > intervaloProva) {
                Tabuleiro.apareceProva();
                ultimaProva = System.currentTimeMillis();
            }

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

            Tabuleiro.mostraTabuleiro();
            contadorRodadas++;
            boolean todosAlunos = alunos.stream()
                    .allMatch(a -> a.estaAprovado() || a.estaReprovado());
            if (todosAlunos) {
                System.out.println("Todos os alunos já foram avaliados na rodada " + contadorRodadas + ". Encerrando simulação.");
                break;
            }
        }
        avaliarAlunos();
    }


    public int rodadaAtual() {
        return contadorRodadas;
    }

    public void avaliarAlunos() {
        for (Aluno aluno : alunos) {
            if (!aluno.estaAprovado() && !aluno.estaReprovado()) {
                aluno.setReprovado(true);
                aluno.setMotivoReprovacao("nota_insuficiente");
            }
        }
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

    public void adicionarAlunosAleatorios(int quantidade, int inteligencia) {
        for (int i = 1; i <= quantidade; i++) {
            int[] posicao = Tabuleiro.geraPosicaoAleatoria();
            Aluno aluno = new Aluno("Aluno" + i, posicao[0], posicao[1], inteligencia);
            adicionarAluno(aluno);
            Tabuleiro.adicionaAgente(aluno);
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

        List<Aluno> alunosOrdenados = new ArrayList<>(alunos);

        alunosOrdenados.sort((a1, a2) -> a1.getNome().compareToIgnoreCase(a2.getNome()));


        List<String> resultado = new ArrayList<>();
        for (Aluno aluno : alunosOrdenados) {
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
                    + aluno.getNotasPerdidas() + " pontos com os professores.\n" + status);
        }

        return resultado;
    }

    public void alterarMaxRodadas(int maxRodadas) {
        this.maxRodadas = maxRodadas;
    }
}