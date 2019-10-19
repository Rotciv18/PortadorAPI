package br.com.rotciv;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class PortadorApplication {

    public static final String EXCHANGE_NAME = "tips_tx";
    public static final String DEFAULT_PARSING_QUEUE = "default_parser_q";
    public static final String ROUTING_KEY = "tips";

    public static void main(String[] args) {
        SpringApplication.run(PortadorApplication.class, args);
    }

    @Bean
    public TopicExchange tipsExchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue defaultParsingQueue(){
        return new Queue(DEFAULT_PARSING_QUEUE);
    }

    @Bean
    public Binding queueToExchangeBiding(){
        return BindingBuilder.bind(defaultParsingQueue()).to(tipsExchange()).with(ROUTING_KEY);
    }

}
