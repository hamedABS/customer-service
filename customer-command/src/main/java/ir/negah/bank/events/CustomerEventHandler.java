package ir.negah.bank.events;

import ir.negah.bank.repository.CustomerCommandEntityRepository;
import org.axonframework.config.ProcessingGroup;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۷
 * TIME: ۱۰:۳۸
 */

@Component
@ProcessingGroup("customer")
public record CustomerEventHandler(CustomerCommandEntityRepository repository) {


    @ExceptionHandler
    public void handle(Exception e) throws Exception {
        throw e;
    }
}
