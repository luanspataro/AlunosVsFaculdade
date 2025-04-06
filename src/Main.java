import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Qual o nível de inteligencia do aluno? (1 - basico ou 2 - inteligente) ");
        int inteligenciaAluno = scan.nextInt();

        Aluno aluno1 = new Aluno("Maria", 2, 3, inteligenciaAluno);
        Aluno aluno2 = new Aluno("João", 5, 6, inteligenciaAluno);
        Professor professor = new Professor(8, 7, 2, 1);

        Simulador simulador = new Simulador();

        simulador.adicionarProfessor(professor);
        simulador.adicionarAluno(aluno1);
        simulador.adicionarAluno(aluno2);

        Tabuleiro.adicionaAgente(aluno1);
        Tabuleiro.adicionaAgente(aluno2);
        Tabuleiro.adicionaAgente(professor);


        long ultimaProva = System.currentTimeMillis();
        long intervaloProva = 1000;

        int contadorRodadas = 0;
        int maxRodadas = 100;

        while (contadorRodadas < maxRodadas) {
            if (System.currentTimeMillis() - ultimaProva > intervaloProva) {
                Tabuleiro.apareceProva();
                ultimaProva = System.currentTimeMillis();
            }

            if (!aluno1.estaAprovado() && !aluno1.estaReprovado()) {
                aluno1.mover();

                if (aluno1.x == professor.x && aluno1.y == professor.y) {
                    aluno1.encontrarProfessor(professor.getForca());
                }
            }

            if (!aluno2.estaAprovado() && !aluno2.estaReprovado()) {
                aluno2.mover();

                if (aluno2.x == professor.x && aluno2.y == professor.y) {
                    aluno2.encontrarProfessor(professor.getForca());
                }
            }

            Agentes.andaAleatorio(professor);

            if (!aluno1.estaAprovado() && !aluno1.estaReprovado()
                    && aluno1.x == professor.x && aluno1.y == professor.y) {
                aluno1.encontrarProfessor(professor.getForca());
            }

            if (!aluno2.estaAprovado() && !aluno2.estaReprovado()
                    && aluno2.x == professor.x && aluno2.y == professor.y) {
                aluno2.encontrarProfessor(professor.getForca());
            }

            Tabuleiro.mostraTabuleiro();
            Tabuleiro.aguardaRodada(200);
            contadorRodadas++;
        }

        simulador.mostrarQuantidadeAlunosAprovados();
        simulador.mostrarQuantidadeAlunosReprovados();
        simulador.mostrarRelatorioAlunos();
        scan.close();
    }
}