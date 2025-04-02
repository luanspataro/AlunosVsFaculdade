public class Aluno extends Agente {
    private int notas;
    private boolean aprovado;
    private boolean reprovado;
    private int inteligencia;

    public Aluno(int x, int y, int inteligencia) {
        super(x, y, "\uD83E\uDDD1");
        this.notas = 0;
        this.aprovado = false;
        this.reprovado = false;
        this.inteligencia = inteligencia;
    }

    public int getNotas() {
        return this.notas;
    }

    public boolean estaAprovado() {
        return aprovado;
    }

//    public boolean estaReprovado() {
//        return reprovado;
//    }

    public void coletarNota() {
        notas += inteligencia;
        if (notas >= 6) {
            aprovado = true;
        }
    }

    public void encontrarProfessor(int forca) {
        if (notas > 0) {
            notas -= forca;
        }
        if (notas <= 0) {
            notas = 0;
            reprovado = true;
        }
    }

    public void mover() {
        Agentes.andaAleatorio(this);
        for (int i = 0; i < Tabuleiro.agentes.size(); i++) {
            Agente agente = Tabuleiro.agentes.get(i);

            if (agente.x == this.x && agente.y == this.y) {
                if (agente instanceof Prova) {
                    coletarNota();
                    Tabuleiro.agentes.remove(agente);
                    if (aprovado) break;
                } else if (agente instanceof Professor) {
                    encontrarProfessor(((Professor) agente).getForca());
                    if (reprovado) break;
                }
            }
        }
    }
}
