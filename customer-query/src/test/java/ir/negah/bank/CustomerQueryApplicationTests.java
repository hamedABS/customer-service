package ir.negah.bank;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

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
    private WebTestClient webTestClient;

    @Test
    void whenCreateCommandThenCustomerCreated(){
    }
}
