package br.com.rotciv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class Cartao extends AbstractEntity{
    private Long numero;
    private String nomePortador;
    private String dataVencimento;
    @ManyToOne
    @JoinColumn(name = "portador_id")
    @JsonIgnore
    private Portador portador;
    @OneToMany(mappedBy = "cartao",
                cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lancamento> lancamentos;

    public Cartao(String nomePortador) {
        this.dataVencimento = generateDataVencimento();
        this.numero = 10000000 + (long) ( Math.random() * (99999999 - 10000000));
        this.nomePortador = nomePortador;
    }

    public Cartao() {
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getNomePortador() {
        return nomePortador;
    }

    public void setNomePortador(String nomePortador) {
        this.nomePortador = nomePortador;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Portador getPortador() {
        return portador;
    }

    public void setPortador(Portador portador) {
        this.portador = portador;
    }


    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public String generateDataVencimento() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 2);

        String data = format.format(calendar.getTime());
        Date inActiveDate = null;
        try {
            inActiveDate = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return data;
    }
}
