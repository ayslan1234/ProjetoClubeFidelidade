package exception;

/**
 * Lançada quando um membro tenta resgatar mais pontos do que possui.
 * <p>
 * É uma checked exception (herda de {@link Exception}), portanto quem chama
 * {@code resgatarPontos()} é obrigado a tratá-la.
 */
public class PontuacaoInsuficienteException extends Exception {
    private int pontosDisponiveis;
    private int pontosNecessarios;

    /**
     * @param pontosDisponiveis saldo atual do membro
     * @param pontosNecessarios pontos exigidos pelo resgate
     */
    public PontuacaoInsuficienteException(int pontosDisponiveis, int pontosNecessarios) {
        this.pontosDisponiveis = pontosDisponiveis;
        this.pontosNecessarios = pontosNecessarios;
    }

    public int getPontosDisponiveis() {
        return pontosDisponiveis;
    }

    public int getPontosNecessarios() {
        return pontosNecessarios;
    }

    /**
     * @return mensagem com o saldo atual, os pontos necessários e quantos faltam
     */
    @Override
    public String getMessage() {
        int faltam = pontosNecessarios - pontosDisponiveis;
        return "Pontuação insuficiente para o resgate!\n" +
                "Saldo atual: " + pontosDisponiveis + " pontos\n" +
                "Necessário: " + pontosNecessarios + " pontos\n" +
                "Faltam: " + faltam + " pontos";
    }
}
