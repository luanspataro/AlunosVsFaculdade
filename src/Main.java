public class Main {
    public static void main(String[] args) {

        Agente aluno1 = new Agente(2, 3, Agentes.aluno);
        Agente aluno2 = new Agente(5, 6, Agentes.aluno);
        Agente faculdade = new Agente(8, 7, Agentes.faculdade);

        Tabuleiro.adicionaAgente(aluno1);
        Tabuleiro.adicionaAgente(aluno2);
        Tabuleiro.adicionaAgente(faculdade);

        boolean colisao = false;
        while (!colisao) {
            if (aluno1.x == faculdade.x && aluno1.y == faculdade.y || aluno2.x == faculdade.x && aluno2.y == faculdade.y) {
                Tabuleiro.mostraTabuleiroExplodido(faculdade.x, faculdade.y);
                System.out.println("colisao");
                colisao = true;
            } else {
                Tabuleiro.mostraTabuleiro();
                Tabuleiro.aguardaRodada(250);
                Agentes.andaAleatorio(aluno1);
                Agentes.andaAleatorio(aluno2);
                Agentes.andaAleatorio(faculdade);
            }
        }

    }
}