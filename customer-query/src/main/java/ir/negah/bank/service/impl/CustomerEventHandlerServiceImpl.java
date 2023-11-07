package ir.negah.bank.service.impl;

import ir.negah.bank.domain.CustomerEntity;
import ir.negah.bank.domain.CustomerStatus;
import ir.negah.bank.domain.Operation;
import ir.negah.bank.domain.OperationDoneByWhenWhy;
import ir.negah.bank.domain.dto.CustomerFullDTO;
import ir.negah.bank.domain.mapper.CustomerMapper;
import ir.negah.bank.events.CustomerCreatedEvent;
import ir.negah.bank.events.CustomerDeletedEvent;
import ir.negah.bank.events.CustomerModifiedEvent;
import ir.negah.bank.events.DoOperationOnCustomerEvent;
import ir.negah.bank.query.GetCustomerByIdQuery;
import ir.negah.bank.repository.CustomerRepository;
import ir.negah.bank.repository.OperationDoneByWhenWhyRepository;
import ir.negah.bank.service.CustomerEventHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.deadletter.DeadLetter;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۳۰
 * TIME: ۱۴:۱۹
 */

@Service
@ProcessingGroup(value = "customer")
@Slf4j
public class CustomerEventHandlerServiceImpl implements CustomerEventHandlerService {

    private CustomerRepository customerRepository;
    private OperationDoneByWhenWhyRepository operationDoneByWhenWhyRepository;
    private QueryUpdateEmitter queryUpdateEmitter;
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerEventHandlerServiceImpl(CustomerRepository customerRepository,
                                           OperationDoneByWhenWhyRepository operationDoneByWhenWhyRepository,
                                           QueryUpdateEmitter queryUpdateEmitter,
                                           CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.operationDoneByWhenWhyRepository = operationDoneByWhenWhyRepository;
        this.queryUpdateEmitter = queryUpdateEmitter;
        this.customerMapper = customerMapper;
    }

    @EventHandler
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void on(CustomerCreatedEvent event, DeadLetter<EventMessage<CustomerCreatedEvent>> deadLetter) {
        //ToDo: what you want to do with deadLetter?

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
        OperationDoneByWhenWhy operationDoneByWhenWhy = new OperationDoneByWhenWhy();
        operationDoneByWhenWhy.setDate(event.getWhen());
        operationDoneByWhenWhy.setOperation(event.getOperation());
        //ToDo: set by who?
        operationDoneByWhenWhy.setBy("currently nobody");
        //ToDo: why??
        operationDoneByWhenWhy.setWhy(null);

        operationDoneByWhenWhyRepository.save(operationDoneByWhenWhy);
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

    @ExceptionHandler
    public void handle(Exception exception) {
        throw new RuntimeException(exception.getMessage());
    }


}
