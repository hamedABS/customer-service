package ir.negah.bank.service;

import ir.negah.bank.domain.CustomerEntity;
import ir.negah.bank.events.CustomerCreatedEvent;
import ir.negah.bank.repository.CustomerRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۳۰
 * TIME: ۱۴:۱۹
 */

@Service
public record EventHandlerService(CustomerRepository customerRepository) {

    @EventHandler
    public void on(CustomerCreatedEvent event){
        CustomerEntity entity = new CustomerEntity();
        BeanUtils.copyProperties(event,entity);
        customerRepository.save(entity);
    }

}
