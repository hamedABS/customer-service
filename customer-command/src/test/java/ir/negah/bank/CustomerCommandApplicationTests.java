package ir.negah.bank;

import ir.negah.bank.aggregate.CustomerAggregate;
import ir.negah.bank.command.DoOperationOnCustomerCommand;
import ir.negah.bank.command.CreateCustomerCommand;
import ir.negah.bank.domain.CustomerStatus;
import ir.negah.bank.domain.Operation;
import ir.negah.bank.domain.mapper.CustomerMapper;
import ir.negah.bank.events.DoOperationOnCustomerEvent;
import ir.negah.bank.events.CustomerCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.UUID;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۹
 * TIME: ۱۵:۱۰
 */
public class CustomerCommandApplicationTests {

    private FixtureConfiguration<CustomerAggregate> fixture;
    private UUID id;
    private CustomerMapper customerMapper;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(CustomerAggregate.class);
        customerMapper = Mappers.getMapper(CustomerMapper.class);
        id = UUID.randomUUID();
    }

    @Test
    void whenCreateCommandThenCustomerCreated(){
        CreateCustomerCommand createCustomerCommand = new CreateCustomerCommand("101324"
        ,"110","/someWhere", CustomerStatus.PENDING,
                "Haj Hamed","Abbaszadeh",
                "Haj Hamed Abbaszadeh", "Hamed Abbaszadeh","09385136659",
                "hamed.abs1997@gmail.com", LocalDate.of(1997,7,12));
        createCustomerCommand.setAggregateId(id.toString());

        CustomerCreatedEvent event = customerMapper.createCommandToCreatedEvent(createCustomerCommand);

        fixture.givenNoPriorActivity()
                .when(createCustomerCommand)
                .expectSuccessfulHandlerExecution()
                .expectEvents(event);
    }

    @Test
    void whenActivateCommandThenCustomerActivated(){
        DoOperationOnCustomerCommand doOperationOnCustomerCommand = new DoOperationOnCustomerCommand(id.toString(), Operation.ACTIVATE);
        CreateCustomerCommand createCustomerCommand = new CreateCustomerCommand("101324"
                ,"110","/someWhere", CustomerStatus.PENDING,
                "Haj Hamed","Abbaszadeh",
                "Haj Hamed Abbaszadeh", "Hamed Abbaszadeh","09385136659",
                "hamed.abs1997@gmail.com", LocalDate.of(1997,7,12));
        createCustomerCommand.setAggregateId(id.toString());

        CustomerCreatedEvent createdEvent = customerMapper.createCommandToCreatedEvent(createCustomerCommand);

        DoOperationOnCustomerEvent event = new DoOperationOnCustomerEvent(doOperationOnCustomerCommand.getAggregateId(),Operation.ACTIVATE);

        fixture.given(createdEvent)
                .when(doOperationOnCustomerCommand)
                .expectSuccessfulHandlerExecution()
                .expectEvents(event);
    }
}
