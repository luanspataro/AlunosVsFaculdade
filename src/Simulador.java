public class Simulador {
    private int rodadas = 0;
    private Integer qntAlunosReprovados = 0;
    private Integer qntAlunosAprovados = 0;
    private static Professor professor;

    public Simulador(Professor professor) {
        if (Simulador.professor == null) {
            Simulador.professor = professor;
        }
    }

    public static void mostrarQuantidadeAlunosReprovados(){
        int qntAlunosReprovados = professor.getAlunosEliminados();
        if(qntAlunosReprovados != 0) {
            System.out.println(qntAlunosReprovados);
        }
    }
}
