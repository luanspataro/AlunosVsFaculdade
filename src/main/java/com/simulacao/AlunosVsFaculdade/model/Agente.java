package com.simulacao.AlunosVsFaculdade.model;

import java.util.Random;

public class Agente {
    static String aluno = "\uD83E\uDDD1";
    static String formado = "\uD83D\uDC68\u200D\uD83C\uDF93";
    static String professor = "\uD83C\uDFEB";
    static String prova = "\uD83D\uDCDD";
    static String explosao = "\uD83D\uDCA5";

    static Random gera = new Random();

    public static void andar(Movimentacao agente, int direcao) {
        // COMANDOS: 0 -> baixo | 1 -> cima | 2 -> esquerda | 3 -> direita

        if (direcao == 0 && agente.x < Tabuleiro.mapa.length - 1) agente.x++;
        else if (direcao == 1 && agente.x > 0) agente.x--;
        else if (direcao == 2 && agente.y > 0) agente.y--;
        else if (direcao == 3 && agente.y < Tabuleiro.mapa[0].length - 1) agente.y++;
    }

    public static void andaAleatorio(Movimentacao agente) {
        andar(agente, gera.nextInt(4));
    }
}