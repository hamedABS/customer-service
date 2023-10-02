package ir.negah.bank.aggregate;

import ir.negah.bank.command.ActivateCustomerCommand;
import ir.negah.bank.command.BaseCommand;
import ir.negah.bank.command.Command;
import ir.negah.bank.command.CreateCustomerCommand;
import ir.negah.bank.domain.CustomerStatus;
import ir.negah.bank.events.CustomerActivatedEvent;
import ir.negah.bank.events.CustomerCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDate;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۷
 * TIME: ۱۰:۲۸
 */
@Aggregate
public class CustomerAggregate {


    @AggregateIdentifier
    private String customerId;

    private String accountNumber;

    private String OfficeCode;

    private String customerImage;

    private CustomerStatus customerStatus;

    private String firstname;

    private String lastname;

    private String fullName;

    private String displayName;

    private String mobileNo;

    private String email;

    private LocalDate dateOfBirth;


    public CustomerAggregate() {
    }

    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand createCustomerCommand) {
        //perform all validations ....


        CustomerCreatedEvent customerCreatedEvent =
                new CustomerCreatedEvent(
                        createCustomerCommand.getCustomerId(),
                        createCustomerCommand.getAccountNumber(),
                        createCustomerCommand.getOfficeCode(),
                        createCustomerCommand.getCustomerImage(),
                        createCustomerCommand.getCustomerStatus(),
                        createCustomerCommand.getFirstname(),
                        createCustomerCommand.getLastname(),
                        createCustomerCommand.getFullName(),
                        createCustomerCommand.getDisplayName(),
                        createCustomerCommand.getMobileNo(),
                        createCustomerCommand.getEmail(),
                        createCustomerCommand.getDateOfBirth()
                );

        AggregateLifecycle.apply(customerCreatedEvent);

    }

    @CommandHandler
    public void handle (ActivateCustomerCommand command){
        if (command.getCommand().equals(Command.ACTIVATE)){
            CustomerActivatedEvent event = new CustomerActivatedEvent(command.getCustomerId());
            AggregateLifecycle.apply(event);
        }
    }


    @EventSourcingHandler
    public void on(CustomerCreatedEvent event) {
        this.firstname = event.getFirstname();
        this.customerId = event.getCustomerId();
        this.lastname = event.getLastname();
        this.fullName = event.getFullName();
        this.displayName = event.getDisplayName();
        this.accountNumber = event.getAccountNumber();
        this.dateOfBirth = event.getDateOfBirth();
        this.mobileNo = event.getMobileNo();
        this.email = event.getEmail();
        this.customerImage = event.getCustomerImage();
        this.OfficeCode = event.getOfficeCode();
        this.customerStatus = event.getCustomerStatus();
    }

    @EventSourcingHandler
    public void on(CustomerActivatedEvent event){
        this.customerId = event.getCustomerId();
        this.customerStatus = CustomerStatus.ACTIVE;
    }
}
