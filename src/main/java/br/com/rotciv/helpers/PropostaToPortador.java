package br.com.rotciv.helpers;

import br.com.rotciv.model.Portador;
import br.com.rotciv.model.Proposta;

public class PropostaToPortador {

    public static Portador propostaToPortador(Proposta proposta){
        Portador portador = new Portador(proposta.getNome(), proposta.getEmail(), proposta.getCpf(),
                                        proposta.getDataNascimento());

        return portador;
    }
}
