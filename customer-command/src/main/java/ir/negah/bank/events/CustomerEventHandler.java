package ir.negah.bank.events;

import ir.negah.bank.domain.CustomerCommandEntity;
import ir.negah.bank.repository.CustomerCommandEntityRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
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

    @EventHandler
    public void on(CustomerCreatedEvent event){
        CustomerCommandEntity entity = new CustomerCommandEntity();
        BeanUtils.copyProperties(event,entity);
        repository.save(entity);
    }

    @ExceptionHandler
    public void handle(Exception e) throws Exception {
        throw e;
    }
}
