package ir.negah.bank.service;

import ir.negah.bank.events.CustomerCreatedEvent;
import ir.negah.bank.events.CustomerDeletedEvent;
import ir.negah.bank.events.CustomerModifiedEvent;
import ir.negah.bank.events.DoOperationOnCustomerEvent;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.deadletter.DeadLetter;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۱/۶
 * TIME: ۱۳:۴۵
 */
public interface CustomerEventHandlerService {

    void on(CustomerCreatedEvent event, DeadLetter<EventMessage<CustomerCreatedEvent>> deadLetter);

    void on(CustomerModifiedEvent event);

    void on(DoOperationOnCustomerEvent event);

    void on(CustomerDeletedEvent event);
}
