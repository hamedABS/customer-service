package ir.negah.bank.service;

import ir.negah.bank.domain.CustomerEntity;
import ir.negah.bank.domain.CustomerStatus;
import ir.negah.bank.events.CustomerActivatedEvent;
import ir.negah.bank.events.CustomerCreatedEvent;
import ir.negah.bank.repository.CustomerRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۳۰
 * TIME: ۱۴:۱۹
 */

@Service
public record EventHandlerService(CustomerRepository customerRepository) {

    @EventHandler
    public void on(CustomerCreatedEvent event) {
        CustomerEntity entity = new CustomerEntity();
        BeanUtils.copyProperties(event, entity);
        customerRepository.save(entity);
    }


    @EventHandler
    public void on(CustomerActivatedEvent event) {
        Optional<CustomerEntity> byEmail = customerRepository.findByEmail(event.getEmail());
        byEmail.get().setCustomerStatus(CustomerStatus.ACTIVE);
        customerRepository.save(byEmail.get());
    }

}
