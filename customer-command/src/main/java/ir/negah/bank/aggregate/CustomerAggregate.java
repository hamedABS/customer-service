package ir.negah.bank.aggregate;

import ir.negah.bank.command.CreateCustomerCommand;
import ir.negah.bank.events.CustomerCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
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

//    @Column(name = "client_status", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private ClientStatus clientStatus;

    private String firstname;

    private String lastname;

    private String fullName;

    private String displayName;

    private String mobileNo;

    private String email;

    private LocalDate dateOfBirth;


    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand createCustomerCommand) {
        //perform all validations ....

        CustomerCreatedEvent customerCreatedEvent =
                new CustomerCreatedEvent(createCustomerCommand.getCustomerId(),
                        createCustomerCommand.getAccountNumber(),
                        createCustomerCommand.getOfficeCode(),
                        createCustomerCommand.getCustomerImage(),
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


    @EventSourcingHandler
    public void on(CustomerCreatedEvent event){
        this.customerId = event.getCustomerId();
        this.firstname = event.getFirstname();
        this.lastname = event.getLastname();
        this.fullName = event.getFullName();
        this.displayName = event.getDisplayName();
        this.accountNumber = event.getAccountNumber();
        this.dateOfBirth = event.getDateOfBirth();
        this.mobileNo = event.getMobileNo();
        this.email = event.getEmail();
        this.customerImage = event.getCustomerImage();
        this.OfficeCode = event.getOfficeCode();
    }
}
