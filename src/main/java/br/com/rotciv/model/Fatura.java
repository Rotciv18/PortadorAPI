package br.com.rotciv.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Fatura extends AbstractEntity{

    private Double total = 0.0;
    @OneToMany(mappedBy = "fatura")
    private List<Lancamento> lancamentos = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    public Fatura() {
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public void atualizaValorTotal(){
        this.total = 0.0;
        for (int i = 0; i < this.lancamentos.size(); i++){
            this.total += this.lancamentos.get(i).getPreco();
        }
    }
}
