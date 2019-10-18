package br.com.rotciv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Lancamento extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    @JsonIgnore
    private Cartao cartao;
    private String nomePortador;
    private String nomeDoProduto;
    private Integer preco;

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

    public Integer getPreco() {
        return preco;
    }

    public void setPreco(Integer preco) {
        this.preco = preco;
    }

    public String getNomePortador() {
        return nomePortador;
    }

    public void setNomePortador(String nomePortador) {
        this.nomePortador = nomePortador;
    }
}
