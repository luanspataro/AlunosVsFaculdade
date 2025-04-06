public class Professor extends Movimentacao {
    private int alunosEliminados;
    private int velocidade;
    private int forca;

    public Professor(int x, int y, int velocidade, int forca) {
        super(x, y, "\uD83D\uDC68\u200D🎓"); // Emoji do professor
        this.alunosEliminados = 0;
        this.velocidade = velocidade;
        this.forca = forca;
    }

    public int getForca() {
        return this.forca;
    }

    public void eliminarAluno(Aluno aluno) {
        aluno.encontrarProfessor(forca);
        alunosEliminados++;
    }

    public Integer getAlunosEliminados() {
        return alunosEliminados;
    }

}