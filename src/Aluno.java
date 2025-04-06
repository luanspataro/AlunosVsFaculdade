public class Aluno extends Movimentacao {
    private int notas;
    private boolean aprovado;
    private boolean reprovado;
    private int inteligencia;
    private int notasPerdidas;
    private String nome;
    private String motivoReprovacao;

    public Aluno(String nome, int x, int y, int inteligencia) {
        super(x, y, "\uD83E\uDDD1");
        this.nome = nome;
        this.notas = 0;
        this.aprovado = false;
        this.reprovado = false;
        this.inteligencia = inteligencia;
        this.notasPerdidas = 0;
        this.motivoReprovacao = "";
    }

    public boolean estaAprovado() {
        return aprovado;
    }

    public boolean estaReprovado() {
        return reprovado;
    }

    public void coletarNota() {
        if (aprovado || reprovado) return;
        notas += inteligencia;
        if (notas >= 6) {
            aprovado = true;
        }
    }

    public void encontrarProfessor(int forca) {
        if (aprovado) return;
        if (notas > 0) {
            int perda = Math.min(forca, notas);
            notas -= perda;
            notasPerdidas += perda;
        }

        if (notas <= 0 && !aprovado) {
            notas = 0;
            if (!reprovado) {
                reprovado = true;
                motivoReprovacao = "professor";
            }
        }
    }

    public void mover() {
        Agente.andaAleatorio(this);
        for (int i = 0; i < Tabuleiro.agentes.size(); i++) {
            Movimentacao agente = Tabuleiro.agentes.get(i);

            if (agente.x == this.x && agente.y == this.y) {
                if (agente instanceof Prova) {
                    coletarNota();
                    Tabuleiro.agentes.remove(agente);
                    if (aprovado) {
                        Tabuleiro.agentes.remove(this);
                        return;
                    }
                } else if (agente instanceof Professor) {
                    encontrarProfessor(((Professor) agente).getForca());
                    if (reprovado) {
                        Tabuleiro.agentes.remove(this);
                        return;
                    }
                }
            }
        }
    }

    public String getNome() {
        return nome;
    }

    public int getNotas() {
        return this.notas;
    }

    public int getNotasPerdidas() {
        return this.notasPerdidas;
    }

    public String getMotivoReprovacao() {
        return motivoReprovacao;
    }

    public void setMotivoReprovacao(String motivoReprovacao) {
        this.motivoReprovacao = motivoReprovacao;
    }

    public void setReprovado(boolean reprovado) {
        this.reprovado = reprovado;
    }
}
