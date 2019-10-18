package br.com.rotciv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Lancamento extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    @JsonIgnore
    private Cartao cartao;
    private String nomePortador;
    private String nomeDoProduto;
    private Double preco;
    @ManyToOne
    @JoinColumn(name = "fatura_id")
    @JsonIgnore
    private Fatura fatura;

    public Lancamento() {
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public String getNomeDoProduto() {
        return nomeDoProduto;
    }

    public void setNomeDoProduto(String nomeDoProduto) {
        this.nomeDoProduto = nomeDoProduto;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getNomePortador() {
        return nomePortador;
    }

    public void setNomePortador(String nomePortador) {
        this.nomePortador = nomePortador;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }
}
