package br.com.rotciv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class PortadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortadorApplication.class, args);
    }

}
