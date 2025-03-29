public class Professor extends Agente {
    private int alunosEliminados;
    private int velocidade;
    private int forca;

    public Professor(int x, int y, int velocidade, int forca) {
        super(x, y, "\uD83D\uDC68\u200D🎓"); // Emoji do professor
        this.alunosEliminados = 0;
        this.velocidade = velocidade;
        this.forca = forca;
    }

    public void eliminarAluno(Aluno aluno) {
        aluno.encontrarProfessor();
        alunosEliminados++;
    }

    public Integer getAlunosEliminados() {
        return alunosEliminados;
    }

}