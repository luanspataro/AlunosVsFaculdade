public class Main {
    public static void main(String[] args) {

        Agente aluno1 = new Agente(2, 3, Agentes.aluno);
        Agente aluno2 = new Agente(5, 6, Agentes.aluno);
        // Agente faculdade = new Agente(8, 7, Agentes.faculdade);
        Professor professor = new Professor(8, 7, 10, 20);
        Aluno aluno = new Aluno(10, 10, "2", 0, false, 10);
        Simulador simulador = new Simulador();

        simulador.adicionarProfessor(professor);
        professor.eliminarAluno(aluno);

        Tabuleiro.adicionaAgente(aluno1);
        Tabuleiro.adicionaAgente(aluno2);
        Tabuleiro.adicionaAgente(professor);

        boolean colisao = false;
        while (!colisao) {
            if (aluno1.x == professor.x && aluno1.y == professor.y || aluno2.x == professor.x && aluno2.y == professor.y) {
                Tabuleiro.mostraTabuleiroExplodido(professor.x, professor.y);
                System.out.println("colisao");
                simulador.mostrarQuantidadeAlunosAprovados();
                simulador.mostrarQuantidadeAlunosReprovados();
                colisao = true;
            } else {
                Tabuleiro.mostraTabuleiro();
                Tabuleiro.aguardaRodada(250);
                Agentes.andaAleatorio(aluno1);
                Agentes.andaAleatorio(aluno2);
                Agentes.andaAleatorio(professor);
            }
        }

    }
}