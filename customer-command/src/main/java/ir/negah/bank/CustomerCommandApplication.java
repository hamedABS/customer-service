package ir.negah.bank;

import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۷
 * TIME: ۱۰:۴۳
 */

@SpringBootApplication
public class CustomerCommandApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerCommandApplication.class, args);
    }

    @Autowired
    public void configure(EventProcessingConfigurer configurer){
        configurer.registerListenerInvocationErrorHandler("customer",
                configuration -> new CustomerServiceEventsErrorHandler());
    }
}

