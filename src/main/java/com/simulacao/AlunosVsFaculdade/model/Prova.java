package com.simulacao.AlunosVsFaculdade.model;

public class Prova extends Movimentacao {
    private int nota;

    public Prova(int x, int y) {
        super(x, y, Agente.prova);
        this.nota = (int) (Math.random() * 11);
    }
}
