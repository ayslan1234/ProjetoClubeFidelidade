package model;

/**
 * Benefícios que podem ser resgatados com pontos e o custo de cada um.
 */
public enum Beneficio {
    CUPOM_DESCONTO("Cupom de R$ 10,00 de desconto", 200),
    FRETE_GRATIS("Frete grátis no próximo pedido", 300),
    BRINDE("Brinde exclusivo do clube", 500),
    MES_GRATIS("Um mês de assinatura grátis", 800);

    private String descricao;
    private int custoPontos;

    Beneficio(String descricao, int custoPontos) {
        this.descricao = descricao;
        this.custoPontos = custoPontos;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCustoPontos() {
        return custoPontos;
    }

    /**
     * Texto exibido no {@code JComboBox} de benefícios da tela.
     */
    @Override
    public String toString() {
        return descricao + " (" + custoPontos + " pts)";
    }
}
