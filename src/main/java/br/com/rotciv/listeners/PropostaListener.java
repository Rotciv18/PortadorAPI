package br.com.rotciv.listeners;

import br.com.rotciv.PortadorApplication;
import br.com.rotciv.model.Cartao;
import br.com.rotciv.model.Fatura;
import br.com.rotciv.model.Portador;
import br.com.rotciv.model.Proposta;
import br.com.rotciv.repository.CartaoRepositorio;
import br.com.rotciv.repository.FaturaRepositorio;
import br.com.rotciv.repository.PortadorRepositorio;
import br.com.rotciv.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class PropostaListener {

    private final PortadorRepositorio portadorDAO;
    private final CartaoRepositorio cartaoDAO;

    public PropostaListener(PortadorRepositorio portadorDAO, CartaoRepositorio cartaoDAO, FaturaRepositorio faturaDAO) {
        this.portadorDAO = portadorDAO;
        this.cartaoDAO = cartaoDAO;
    }

    @RabbitListener(queues = PortadorApplication.DEFAULT_PARSING_QUEUE)
    public void receiveProposta (final Proposta proposta) throws IOException, MessagingException {

        Portador portador = proposta.toPortador();
        Cartao cartao = new Cartao(portador.getNome());
        cartao.setPortador(portador);
        Fatura fatura = new Fatura();
        fatura.setCartao(cartao);
        cartao.setFatura(fatura);

        portadorDAO.save(portador);
        cartaoDAO.save(cartao);

        //Envia um email de confirmação
        EmailService.sendmail();
    }
}
