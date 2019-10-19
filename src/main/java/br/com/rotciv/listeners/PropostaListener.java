package br.com.rotciv.listeners;

import br.com.rotciv.PortadorApplication;
import br.com.rotciv.model.Cartao;
import br.com.rotciv.model.Fatura;
import br.com.rotciv.model.Portador;
import br.com.rotciv.model.Proposta;
import br.com.rotciv.repository.CartaoRepositorio;
import br.com.rotciv.repository.FaturaRepositorio;
import br.com.rotciv.repository.PortadorRepositorio;
import br.com.rotciv.throwableResponses.ThrowableOkStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PropostaListener {

    private final PortadorRepositorio portadorDAO;
    private final CartaoRepositorio cartaoDAO;
    private final FaturaRepositorio faturaDAO;

    public PropostaListener(PortadorRepositorio portadorDAO, CartaoRepositorio cartaoRepositorio, CartaoRepositorio cartaoDAO, FaturaRepositorio faturaDAO) {
        this.portadorDAO = portadorDAO;
        this.cartaoDAO = cartaoDAO;
        this.faturaDAO = faturaDAO;
    }

    @RabbitListener(queues = PortadorApplication.DEFAULT_PARSING_QUEUE)
    public void receiveProposta (final Proposta proposta){

        Portador portador = proposta.toPortador();
        Cartao cartao = new Cartao(portador.getNome());
        cartao.setPortador(portador);
        Fatura fatura = new Fatura();
        fatura.setCartao(cartao);
        cartao.setFatura(fatura);

        portadorDAO.save(portador);
        cartaoDAO.save(cartao);
        //faturaDAO.save(fatura);

        //return new ResponseEntity<>("Portador cadastrado", HttpStatus.OK);
    }
}
