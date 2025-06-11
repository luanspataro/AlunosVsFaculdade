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

    public void comecarSimulacao(int dificuldade, int velocidadeProfessor, int forcaProfessor) {
        long ultimaProva = System.currentTimeMillis();
        long intervaloProva = 0;

        if (dificuldade == 1) { // Fácil
            intervaloProva = 1000;
            adicionarProfessores(1, velocidadeProfessor, forcaProfessor);
        } else if (dificuldade == 2) { // Médio
            intervaloProva = 1000;
            adicionarProfessores(2, velocidadeProfessor, forcaProfessor);
        } else if (dificuldade == 3) { // Difícil
            intervaloProva = 1000;
            adicionarProfessores(3, velocidadeProfessor, forcaProfessor);
        }

        while (this.contadorRodadas <= maxRodadas) {
            if (System.currentTimeMillis() - ultimaProva > intervaloProva) {
                Tabuleiro.apareceProva(dificuldade);
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

            // Movimentar os professores de acordo com sua velocidade
            for (Professor prof : professores) {
                for (int i = 0; i < prof.getVelocidade(); i++) {
                    Agente.andaAleatorio(prof);
                    int x = prof.x;
                    int y = prof.y;
                    for (int z = 0; z < Tabuleiro.agentes.size(); z++) {
                        Movimentacao agente = Tabuleiro.agentes.get(z);
                        if (agente.x == prof.x && agente.y == prof.y) {
                            if (agente instanceof Prova) {
                                Tabuleiro.agentes.remove(agente);
                                Tabuleiro.apareceProva(3);
                            }
                        }
                    }
                    Professor professor = new Professor(x,y,velocidadeProfessor, forcaProfessor);
                }
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

    private void adicionarProfessores(int quantidade, int velocidadeProfessor, int forcaProfessor) {
        for (int i = 0; i < quantidade; i++) {
            int[] posicaoProfessor = Tabuleiro.geraPosicaoAleatoria();
            // Cria o professor utilizando a velocidade e a força passadas pelo usuário.
            Professor professor = new Professor(posicaoProfessor[0], posicaoProfessor[1], velocidadeProfessor, forcaProfessor);
            adicionarProfessor(professor);
            Tabuleiro.adicionaAgente(professor);
        }
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


