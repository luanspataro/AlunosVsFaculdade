public class Prova extends Agente {
    private int nota;

    public Prova(int x, int y) {
        super(x, y, Agentes.prova);
        this.nota = (int) (Math.random() * 11);
    }
}
