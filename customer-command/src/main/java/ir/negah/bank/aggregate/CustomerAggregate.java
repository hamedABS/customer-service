package ir.negah.bank.aggregate;

import ir.negah.bank.command.CreateCustomerCommand;
import ir.negah.bank.command.DeleteCustomerCommand;
import ir.negah.bank.command.DoOperationOnCustomerCommand;
import ir.negah.bank.command.UpdateCustomerCommand;
import ir.negah.bank.domain.CustomerStatus;
import ir.negah.bank.domain.Operation;
import ir.negah.bank.domain.mapper.CustomerMapper;
import ir.negah.bank.events.CustomerCreatedEvent;
import ir.negah.bank.events.CustomerDeletedEvent;
import ir.negah.bank.events.CustomerModifiedEvent;
import ir.negah.bank.events.DoOperationOnCustomerEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۷
 * TIME: ۱۰:۲۸
 */
@Aggregate
@Slf4j
@ProcessingGroup("customer")
public class CustomerAggregate {


    @AggregateIdentifier
    private String aggregateId;

    private Long id;

    private String accountNumber;

    private String officeCode;

    private String customerImage;

    private String singImage;

    private CustomerStatus customerStatus;

    private String firstname;

    private String lastname;

    private String fullName;

    private String displayName;

    private String mobileNo;

    private Boolean deleted;

    private String email;

    private LocalDate dateOfBirth;

    private CustomerMapper customerMapper;


    public CustomerAggregate() {
    }

    @Autowired
    public CustomerAggregate(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand createCustomerCommand) {
        //perform all validations ....
        log.debug(CreateCustomerCommand.class.getSimpleName() + " Processing ...");
        CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);
        CustomerCreatedEvent createdEvent = mapper.createCommandToCreatedEvent(createCustomerCommand);
        createdEvent.setAggregateId(createCustomerCommand.getAggregateId());
        try {
            AggregateLifecycle.apply(createdEvent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @CommandHandler
    public void handle(DeleteCustomerCommand command) {
        log.debug(DeleteCustomerCommand.class.getSimpleName() + " Processing ...");

        CustomerDeletedEvent event = new CustomerDeletedEvent();
        event.setAggregateId(command.getAggregateId());
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(DoOperationOnCustomerCommand command) {
        log.debug(DoOperationOnCustomerCommand.class.getSimpleName() + " Processing ...");

        DoOperationOnCustomerEvent event = new DoOperationOnCustomerEvent(command.getOperation(), command.getWhen());
        event.setAggregateId(command.getAggregateId());

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateCustomerCommand command) {
        log.debug(UpdateCustomerCommand.class.getSimpleName() + " Processing ...");
        CustomerModifiedEvent modifiedEvent = customerMapper.updateCommandToModifiedEvent(command);
        modifiedEvent.setAggregateId(command.getAggregateId());
        AggregateLifecycle.apply(modifiedEvent);
    }


    @EventSourcingHandler
    public void on(CustomerCreatedEvent event) {
        this.firstname = event.getFirstname();
        this.aggregateId = event.getAggregateId();
        this.lastname = event.getLastname();
        this.fullName = event.getFullName();
        this.displayName = event.getDisplayName();
        this.accountNumber = event.getAccountNumber();
        this.dateOfBirth = event.getDateOfBirth();
        this.mobileNo = event.getMobileNumber();
        this.email = event.getEmail();
        this.customerImage = event.getCustomerImage();
        this.singImage = event.getSignImage();
        this.officeCode = event.getOfficeCode();
        this.customerStatus = event.getCustomerStatus();
        this.deleted = false;
    }


    @EventSourcingHandler
    public void on(DoOperationOnCustomerEvent event) {
        this.aggregateId = event.getAggregateId();
        if (event.getOperation().equals(Operation.ACTIVATE)) {
            this.customerStatus = CustomerStatus.ACTIVE;
        } else if (event.getOperation().equals(Operation.CLOSURE)) {
            this.customerStatus = CustomerStatus.CLOSED;
        } else if (event.getOperation().equals(Operation.REJECTION)) {
            this.customerStatus = CustomerStatus.REJECTED;
        }
    }

    @EventSourcingHandler
    public void on(CustomerModifiedEvent event) {
        this.firstname = event.getFirstname();
        this.aggregateId = event.getAggregateId();
        this.lastname = event.getLastname();
        this.fullName = event.getFullName();
        this.displayName = event.getDisplayName();
        this.accountNumber = event.getAccountNumber();
        this.dateOfBirth = event.getDateOfBirth();
        this.mobileNo = event.getMobileNo();
        this.email = event.getEmail();
        this.customerImage = event.getCustomerImage();
        this.singImage = event.getSignImage();
        this.officeCode = event.getOfficeCode();
        this.customerStatus = event.getCustomerStatus();
    }

    @EventSourcingHandler
    public void on(CustomerDeletedEvent event) {
        this.deleted = true;
    }
}
