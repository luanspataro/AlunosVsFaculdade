package com.simulacao.AlunosVsFaculdade.model;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    public static String [][] mapa = new String[9][19];
    public static List<Movimentacao> agentes = new ArrayList<>();

    public static void iniciaTabuleiro() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                mapa[i][j] = " ";
            }
        }
        posicionaAgentes();
    }

    public static void mostraTabuleiro() {
        System.out.println("\n \n \n");
        iniciaTabuleiro();
        posicionaAgentes();

        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void mostraTabuleiroExplodido(int x, int y) {
        agentes.removeIf(a -> a.x == x && a.y == y); // Remove os agentes da coordenada
        Movimentacao explosao = new Movimentacao(x, y, Agente.explosao);
        Tabuleiro.adicionaAgente(explosao);
        Tabuleiro.mostraTabuleiro();
    }

    public static void posicionaAgentes() {
        for (Movimentacao agente : agentes) {
            mapa[agente.x][agente.y] = agente.emoji;
        }
    }

    public static void aguardaRodada(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void adicionaAgente(Movimentacao agente) {
        agentes.add(agente);
    }

    public static void posicionaAgente(int x, int y, String agente) {
        mapa[x][y] = agente;
    }

    public static void apareceProva(){
        int [] posicao = geraPosicaoAleatoria();
        Prova prova = new Prova(posicao[0], posicao[1]);
        adicionaAgente(prova);
    }

    public static int[] geraPosicaoAleatoria(){
        int [] posicao = new int[2];
        posicao[0] = (int) (Math.random() * mapa.length);
        posicao[1] = (int) (Math.random() * mapa[0].length);

        return posicao;
    }
}