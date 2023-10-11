package ir.negah.bank;

import ir.negah.bank.domain.CustomerEntity;
import ir.negah.bank.projection.CustomerProjection;
import ir.negah.bank.service.CustomerEventHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۹
 * TIME: ۱۳:۵۶
 */

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class CustomerQueryApplicationTests {

    @Autowired
    private CustomerProjection customerProjection;

    @Autowired
    private EventGateway eventGateway;

    @Autowired
    private CustomerEventHandler customerEventHandler;

    private String customerId;

    @BeforeEach
    void setUp(){
        customerId = UUID.randomUUID().toString();
        CustomerEntity customer = new CustomerEntity();
    }
}
