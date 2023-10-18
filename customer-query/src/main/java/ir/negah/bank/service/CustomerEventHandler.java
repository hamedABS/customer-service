package ir.negah.bank.service;

import ir.negah.bank.domain.CustomerEntity;
import ir.negah.bank.domain.CustomerStatus;
import ir.negah.bank.domain.Operation;
import ir.negah.bank.domain.dto.CustomerFullDTO;
import ir.negah.bank.domain.mapper.CustomerMapper;
import ir.negah.bank.events.CustomerCreatedEvent;
import ir.negah.bank.events.CustomerDeletedEvent;
import ir.negah.bank.events.CustomerModifiedEvent;
import ir.negah.bank.events.DoOperationOnCustomerEvent;
import ir.negah.bank.query.GetCustomerByIdQuery;
import ir.negah.bank.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۳۰
 * TIME: ۱۴:۱۹
 */

@Service
@ProcessingGroup("customer")
@Slf4j
public record CustomerEventHandler(CustomerRepository customerRepository,
                                   QueryUpdateEmitter queryUpdateEmitter,
                                   CustomerMapper customerMapper) {

    @EventHandler
    public void on(CustomerCreatedEvent event) {
        log.debug("Handling " + CustomerCreatedEvent.class.getSimpleName());
        CustomerEntity entity = new CustomerEntity();
        BeanUtils.copyProperties(event, entity);
        customerRepository.save(entity);
    }

    @EventHandler
    public void on(CustomerModifiedEvent event) {
        log.debug("Handling " + CustomerModifiedEvent.class.getSimpleName());
        CustomerEntity byAggregateId = getCustomerByAggregateId(event.getAggregateId());
        BeanUtils.copyProperties(event, byAggregateId);
        customerRepository.save(byAggregateId);
    }


    @EventHandler
    public void on(DoOperationOnCustomerEvent event) {
        log.debug("Handling " + DoOperationOnCustomerEvent.class.getSimpleName());

        CustomerEntity byAggregateId = getCustomerByAggregateId(event.getAggregateId());

        if (event.getOperation().equals(Operation.ACTIVATE)) {
            byAggregateId.setCustomerStatus(CustomerStatus.ACTIVE);
        } else if (event.getOperation().equals(Operation.CLOSURE)) {
            byAggregateId.setCustomerStatus(CustomerStatus.CLOSED);
        } else if (event.getOperation().equals(Operation.REJECTION)) {
            byAggregateId.setCustomerStatus(CustomerStatus.REJECTED);
        }

        CustomerEntity saved = customerRepository.save(byAggregateId);
        CustomerFullDTO customerFullDTO = customerMapper.entityToFullDTO(saved);

        queryUpdateEmitter.emit(m ->
                ((GetCustomerByIdQuery) m.getPayload()).getAggregateId().equals(event.getAggregateId()), customerFullDTO);
    }

    @EventHandler
    public void on(CustomerDeletedEvent event) {
        log.debug("Handling " + CustomerDeletedEvent.class.getSimpleName());
        CustomerEntity byAggregateId = getCustomerByAggregateId(event.getAggregateId());
        byAggregateId.setDeleted(true);
        customerRepository.save(byAggregateId);

    }

    private CustomerEntity getCustomerByAggregateId(String aggregateId) {
        return customerRepository.findByAggregateId(aggregateId).get();
    }

}
