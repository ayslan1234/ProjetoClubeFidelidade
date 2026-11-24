package model;

/**
 * Membro do plano Básico: ganha 1 ponto a cada R$ 1,00 gasto.
 */
public class MembroBasico extends MembroClube {

    public MembroBasico(int id, String nome, String email) {
        super(id, nome, email);
    }

    @Override
    public int calcularPontos(double valorCompra) {
        return (int) valorCompra;
    }

    @Override
    public String getPlano() {
        return "Básico";
    }
}
