import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Simulador {
    private int contadorRodadas = 1;
    private int quantidadeTotalDeRodadas = 0;
    private int maxRodadas = 100;
    private Set<Professor> professores;
    private Set<Aluno> alunos;

    public Simulador() {
        this.professores = new HashSet<>();
        this.alunos = new HashSet<>();
    }

    public void comecarSimulacao(int dificuldade) {
        long ultimaProva   = System.currentTimeMillis();
        long intervaloProva = 1000;

        if (dificuldade == 1) {
            Professor prof1 = new Professor(8, 7, 2, 1);
            adicionarProfessor(prof1);
            Tabuleiro.adicionaAgente(prof1);
        }

        while (this.contadorRodadas <= maxRodadas) {
            if (System.currentTimeMillis() - ultimaProva > intervaloProva) {
                Tabuleiro.apareceProva();
                ultimaProva = System.currentTimeMillis();
            }

            Tabuleiro.mostraTabuleiro();
            Tabuleiro.aguardaRodada(400);

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

    public void mostrarQuantidadeAlunosReprovados() {
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

        System.out.println("Alunos reprovados pelo professor: " + totalProfessor);
        System.out.println("Alunos reprovados por não ter atingido a nota: " + totalNotaInsuficiente);
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

    public void mostrarRelatorioAlunos() {
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

            System.out.println(aluno.getNome() + " na posição (" + aluno.x + "," + aluno.y + ") tem "
                    + aluno.getNotas() + " pontos no total. Perdeu "
                    + aluno.getNotasPerdidas() + " pontos com os professores. " + status);
        }
    }

    public void alterarMaxRodadas(int maxRodadas) {
        this.maxRodadas = maxRodadas;
    }
}