package interfaces;

import exception.PontuacaoInsuficienteException;

/**
 * Contrato de quem participa do programa de pontos do clube.
 */
public interface Bonificavel {

    /**
     * Calcula quantos pontos uma compra gera. Cada plano tem a sua regra.
     *
     * @param valorCompra valor da compra em reais
     * @return pontos gerados pela compra
     */
    int calcularPontos(double valorCompra);

    /**
     * Soma ao saldo os pontos gerados por uma compra.
     *
     * @param valorCompra valor da compra em reais
     * @return {@code false} se o valor for menor ou igual a zero
     */
    boolean acumularPontos(double valorCompra);

    /**
     * Desconta pontos do saldo.
     *
     * @param pontos quantidade de pontos a resgatar
     * @throws PontuacaoInsuficienteException se o saldo for menor que {@code pontos}
     */
    void resgatarPontos(int pontos) throws PontuacaoInsuficienteException;

    int getPontos();

    /**
     * @return "Bronze" (até 499 pts), "Prata" (500 a 999 pts) ou "Ouro" (1000 pts ou mais)
     */
    default String getNivel() {
        if (getPontos() >= 1000) {
            return "Ouro";
        } else if (getPontos() >= 500) {
            return "Prata";
        }
        return "Bronze";
    }

    /**
     * Converte pontos em reais. Cada ponto vale R$ 0,05.
     */
    static double converterEmReais(int pontos) {
        return pontos * 0.05;
    }
}
