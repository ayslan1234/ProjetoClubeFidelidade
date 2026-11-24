package model;

/**
 * Membro do plano Premium: ganha 2 pontos a cada R$ 1,00 gasto e um bônus
 * de 50 pontos em compras a partir de R$ 100,00.
 */
public class MembroPremium extends MembroClube {
    private static final int BONUS_COMPRA_ALTA = 50;

    public MembroPremium(int id, String nome, String email) {
        super(id, nome, email);
    }

    @Override
    public int calcularPontos(double valorCompra) {
        int pontosCompra = (int) (valorCompra * 2);
        if (valorCompra >= 100) {
            pontosCompra += BONUS_COMPRA_ALTA;
        }
        return pontosCompra;
    }

    @Override
    public String getPlano() {
        return "Premium";
    }
}
