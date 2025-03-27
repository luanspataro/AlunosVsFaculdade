public class Aluno extends Agente {
    private int notas;
    private boolean aprovado;
    private int inteligencia;

    public Aluno(int x, int y, String emoji, int notas, boolean aprovado, int inteligencia) {
        super(x, y, emoji);
        this.notas = 0;
        this.aprovado = false;
        this.inteligencia = inteligencia;
    }

    public void setNotas(int notas) {
        this.notas = notas;
    }

    public int getNotas(){
        return this.notas;
    }

    public Aluno(int x, int y){
        super(x, y, "\uD83E\uDDD1"); // emoji e posição
        this.notas = 0;
        this.aprovado = false;
    }

    public void coletarNota() {
        notas++;
        if (notas >= 6) {
            aprovado = true;
        }
    }

    public void encontrarProfessor() {
        if (notas > 0) {
            notas--;
        }
    }
    // implementar a lógica do fim do semetre no tabuleiro

    public boolean estaAprovado() {
        return aprovado;
    }
    // colocar no tabuleiro
}