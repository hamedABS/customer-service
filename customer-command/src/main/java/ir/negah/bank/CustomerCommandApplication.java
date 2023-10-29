package ir.negah.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۷
 * TIME: ۱۰:۴۳
 */

@SpringBootApplication
@EnableFeignClients
public class CustomerCommandApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerCommandApplication.class, args);
    }
}

