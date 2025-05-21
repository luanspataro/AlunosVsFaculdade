import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Qual o nível de inteligencia do aluno? (1 - básico ou 2 - inteligente) ");
        int inteligenciaAluno = scan.nextInt();

        System.out.println("Escolha o nível de dificuldade:");
        System.out.println("1 - Particular (Fácil), 2 - Federal (Médio) ou 3 - FATEC (Difícil):");
        int dificuldade = scan.nextInt();

        System.out.println("Digite a velocidade do professor (quantas casas por rodada):");
        int velocidadeProfessor = scan.nextInt();

        // Solicita também a força do professor:
        System.out.println("Digite a força do professor (quantas notas ele retira):");
        int forcaProfessor = scan.nextInt();

        int[] posicaoALuno1 = Tabuleiro.geraPosicaoAleatoria();
        Aluno aluno1 = new Aluno("Maria", posicaoALuno1[0], posicaoALuno1[1], inteligenciaAluno);
        int[] posicaoALuno2 = Tabuleiro.geraPosicaoAleatoria();
        Aluno aluno2 = new Aluno("João", posicaoALuno2[0], posicaoALuno2[1], inteligenciaAluno);

        Simulador simulador = new Simulador();

        simulador.adicionarAluno(aluno1);
        simulador.adicionarAluno(aluno2);

        Tabuleiro.adicionaAgente(aluno1);
        Tabuleiro.adicionaAgente(aluno2);

        // Passa a dificuldade, a velocidade e a força do professor para o simulador:
        simulador.comecarSimulacao(dificuldade, velocidadeProfessor, forcaProfessor);
        scan.close();
    }
}
