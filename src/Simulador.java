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

    public void mostrarQuantidadeAlunosReprovados(){
        int total = 0;
        for (Professor prof : professores) {
            total += prof.getAlunosEliminados();
        }
        System.out.println("Alunos reprovados: " + total);
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

    public void mostrarNotasAlunos(){
        for (Aluno aluno : alunos) {
            System.out.println("Aluno na posição (" + aluno.x + "," + aluno.y + ") tem " + aluno.getNotas() + " pontos.");
        }
    }
}