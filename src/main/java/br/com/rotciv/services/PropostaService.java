package br.com.rotciv.services;

import br.com.rotciv.PortadorApplication;
import br.com.rotciv.model.Proposta;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PropostaService {

    private final RabbitTemplate rabbitTemplate;

    public PropostaService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarProposta(Proposta proposta){
        rabbitTemplate.convertAndSend(PortadorApplication.EXCHANGE_NAME, PortadorApplication.ROUTING_KEY, proposta);
        //rabbitTemplate.convertAndSend(proposta);
    }
}
