import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Qual o nível de inteligencia do aluno? (1 - basico ou 2 - inteligente) ");
        int inteligenciaAluno = scan.nextInt();

        Aluno aluno1 = new Aluno(2, 3, inteligenciaAluno);
        Aluno aluno2 = new Aluno(5, 6, inteligenciaAluno);
        Professor professor = new Professor(8, 7, 2, 1);

        Simulador simulador = new Simulador();

        simulador.adicionarProfessor(professor);
        simulador.adicionarAluno(aluno1);
        simulador.adicionarAluno(aluno2);

        Tabuleiro.adicionaAgente(aluno1);
        Tabuleiro.adicionaAgente(aluno2);
        Tabuleiro.adicionaAgente(professor);


        long ultimaProva = System.currentTimeMillis();
        long intervaloProva = 3000;

        int contadorRodadas = 0;
        int maxRodadas = 100;

        while (contadorRodadas < maxRodadas) {
            if (System.currentTimeMillis() - ultimaProva > intervaloProva) {
                Tabuleiro.apareceProva();
                ultimaProva = System.currentTimeMillis();
            }

            Tabuleiro.mostraTabuleiro();
            Tabuleiro.aguardaRodada(200);
            aluno1.mover();
            aluno2.mover();
            Agentes.andaAleatorio(professor);
            contadorRodadas++;
        }

        simulador.mostrarQuantidadeAlunosAprovados();
        simulador.mostrarQuantidadeAlunosReprovados();
        simulador.mostrarNotasAlunos();
        scan.close();
    }
}