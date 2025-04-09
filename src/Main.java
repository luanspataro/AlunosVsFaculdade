import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Qual o nível de inteligencia do aluno? (1 - basico ou 2 - inteligente) ");
        int inteligenciaAluno = scan.nextInt();
        System.out.println("Escolha o nível de dificuldade:");
        System.out.println("1 - Fácil, 2 - Médio - 3 - Difícil:");
        int dificuldade = scan.nextInt();

        int [] posicaoALuno1 = Tabuleiro.geraPosicaoAleatoria();
        Aluno aluno1 = new Aluno("Maria", posicaoALuno1[0], posicaoALuno1[1], inteligenciaAluno);
        int [] posicaoALuno2 = Tabuleiro.geraPosicaoAleatoria();
        Aluno aluno2 = new Aluno("João", posicaoALuno2[0], posicaoALuno2[1], inteligenciaAluno);
        //Professor professor = new Professor(posicaoProfessor[0], posicaoProfessor[1], 2, 1);

        Simulador simulador = new Simulador();

        simulador.adicionarAluno(aluno1);
        simulador.adicionarAluno(aluno2);

        Tabuleiro.adicionaAgente(aluno1);
        Tabuleiro.adicionaAgente(aluno2);

        simulador.comecarSimulacao(dificuldade);
        scan.close();
    }
}