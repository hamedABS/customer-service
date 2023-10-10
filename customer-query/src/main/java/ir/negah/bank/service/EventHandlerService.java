package ir.negah.bank.service;

import ir.negah.bank.domain.CustomerEntity;
import ir.negah.bank.domain.CustomerStatus;
import ir.negah.bank.domain.dto.CustomerFullDTO;
import ir.negah.bank.domain.mapper.CustomerMapper;
import ir.negah.bank.events.CustomerActivatedEvent;
import ir.negah.bank.events.CustomerCreatedEvent;
import ir.negah.bank.exception.RequestedNotFoundException;
import ir.negah.bank.query.GetCustomerByIdQuery;
import ir.negah.bank.repository.CustomerRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۳۰
 * TIME: ۱۴:۱۹
 */

@Service
public record EventHandlerService(CustomerRepository customerRepository,
                                  QueryUpdateEmitter queryUpdateEmitter,
                                  CustomerMapper customerMapper) {

    @EventHandler
    public void on(CustomerCreatedEvent event) {
        CustomerEntity entity = new CustomerEntity();
        BeanUtils.copyProperties(event, entity);
        customerRepository.save(entity);
    }


    @EventHandler
    public void on(CustomerActivatedEvent event) {
        Optional<CustomerEntity> byAggregateId = customerRepository.findByAggregateId(event.getAggregateId());
        byAggregateId.get().setCustomerStatus(CustomerStatus.ACTIVE);

        CustomerEntity saved = customerRepository.save(byAggregateId.get());
        CustomerFullDTO customerFullDTO = customerMapper.entityToFullDTO(saved);

        queryUpdateEmitter.emit(m ->
                ((GetCustomerByIdQuery) m.getPayload()).getAggregateId().equals(event.getAggregateId()), customerFullDTO);
    }

}
