package ir.negah.bank;

import ir.negah.bank.service.DeadLetterProcessor;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.modelling.saga.repository.inmemory.InMemorySagaStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;


/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۱
 * TIME: ۱۵:۵۲
 */
@SpringBootApplication
public class CustomerQueryApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerQueryApplication.class, args);
    }


    @Bean
    public SagaStore sagaStore() {
        return new InMemorySagaStore();
    }


}
