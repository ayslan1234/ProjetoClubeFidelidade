package controller;

import view.TelaClube;

/**
 * Ponto de entrada do sistema: abre a tela principal do clube.
 */
public class App {

    public static void main(String[] args) {
        TelaClube tela = new TelaClube();
        tela.setVisible(true);
    }
}
