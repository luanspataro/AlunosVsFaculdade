import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Qual o nível de inteligencia do aluno? (1 - basico ou 2 - inteligente) ");
        int inteligenciaAluno = scan.nextInt();
        System.out.println("Escolha o nível de dificuldade:");
        System.out.println("1 - Fácil, 2 - Médio - 3 - Difícil:");
        int dificuldade = scan.nextInt();


        Aluno aluno1 = new Aluno("Maria", 2, 3, inteligenciaAluno);
        Aluno aluno2 = new Aluno("João", 5, 6, inteligenciaAluno);
        Professor professor = new Professor(8, 7, 2, 1);

        Simulador simulador = new Simulador();

        simulador.adicionarAluno(aluno1);
        simulador.adicionarAluno(aluno2);

        Tabuleiro.adicionaAgente(aluno1);
        Tabuleiro.adicionaAgente(aluno2);

        simulador.comecarSimulacao(dificuldade);
        scan.close();
    }
}