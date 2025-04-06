import java.util.HashSet;
import java.util.Set;

public class Simulador {
    private int rodadas = 0;
    private Set<Professor> professores;
    private Set<Aluno> alunos;

    public Simulador() {
        this.professores = new HashSet<>();
        this.alunos = new HashSet<>();
    }

    public void adicionarProfessor(Professor professor){
        if(professor != null){
            professores.add(professor);
        }
    }

    public void adicionarAluno(Aluno aluno){
        if(aluno != null)
            alunos.add(aluno);
    }

    public void mostrarQuantidadeAlunosReprovados() {
        int totalProfessor = 0;
        int totalNotaInsuficiente = 0;

        for (Aluno aluno : alunos) {
            if (aluno.estaReprovado() && "professor".equals(aluno.getMotivoReprovacao())) {
                totalProfessor++;
            } else if (!aluno.estaAprovado() && !aluno.estaReprovado()) {
                aluno.setReprovado(true);
                aluno.setMotivoReprovacao("nota_insuficiente");
                totalNotaInsuficiente++;
            }
        }

        System.out.println("Alunos reprovados pelo professor: " + totalProfessor);
        System.out.println("Alunos reprovados por não ter atingido a nota: " + totalNotaInsuficiente);
    }

    public void mostrarQuantidadeAlunosAprovados(){
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
}