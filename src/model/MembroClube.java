package model;

import exception.PontuacaoInsuficienteException;
import interfaces.Bonificavel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Entidade principal do sistema. Guarda os dados comuns a todos os membros;
 * cada plano define como calcula os pontos em {@link #calcularPontos(double)}.
 * <p>
 * A ordem natural ({@link #compareTo}) e a igualdade ({@link #equals}) são pelo ID.
 */
public abstract class MembroClube implements Bonificavel, Comparable<MembroClube> {
    private int id;
    private String nome;
    private String email;
    private LocalDate dataAdesao;
    private int pontos;

    public MembroClube(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataAdesao = LocalDate.now();
        this.pontos = 0;
    }

    /**
     * @return nome do plano do membro ("Básico" ou "Premium")
     */
    public abstract String getPlano();

    @Override
    public boolean acumularPontos(double valorCompra) {
        if (valorCompra <= 0) {
            return false;
        }
        pontos += calcularPontos(valorCompra);
        return true;
    }

    @Override
    public void resgatarPontos(int pontosResgate) throws PontuacaoInsuficienteException {
        if (pontosResgate > pontos) {
            throw new PontuacaoInsuficienteException(pontos, pontosResgate);
        }
        pontos -= pontosResgate;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataAdesao() {
        return dataAdesao;
    }

    @Override
    public int getPontos() {
        return pontos;
    }

    @Override
    public int compareTo(MembroClube outro) {
        return Integer.compare(this.id, outro.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MembroClube)) return false;
        MembroClube outro = (MembroClube) o;
        return id == outro.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * @return ficha completa do membro, exibida na pesquisa
     */
    public String exibirDetalhes() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "===== Dados do Membro =====\n" +
                "ID: " + id + "\n" +
                "Nome: " + nome + "\n" +
                "E-mail: " + email + "\n" +
                "Plano: " + getPlano() + "\n" +
                "Data de adesão: " + dataAdesao.format(formato) + "\n" +
                "Pontos: " + pontos + " (equivale a R$ " +
                String.format("%.2f", Bonificavel.converterEmReais(pontos)) + ")\n" +
                "Nível: " + getNivel() + "\n";
    }

    /**
     * @return linha formatada em colunas, usada nas listagens e no ranking
     */
    @Override
    public String toString() {
        return String.format("%-5d %-20s %-8s %7d  %-6s", id, nome, getPlano(), pontos, getNivel());
    }
}
