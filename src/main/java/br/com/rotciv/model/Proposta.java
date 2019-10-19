package br.com.rotciv.model;

import java.io.Serializable;

public class Proposta implements Serializable {
    private String nome;
    private String email;
    private String cpf;
    private String dataNascimento;
    private String nomeDoPai;
    private String nomeDaMae;

    public Proposta() {
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomeDoPai() {
        return nomeDoPai;
    }

    public void setNomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
    }

    public String getNomeDaMae() {
        return nomeDaMae;
    }

    public void setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
    }

    public Portador toPortador(){
        Portador portador = new Portador(this.nome, this.email, this.cpf,
                this.dataNascimento);

        return portador;
    }
}
