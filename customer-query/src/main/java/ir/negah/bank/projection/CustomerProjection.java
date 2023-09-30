package ir.negah.bank.projection;

import ir.negah.bank.query.GetCustomerQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۳۰
 * TIME: ۹:۴۷
 */
@Component
public record CustomerProjection(CustomerRepository customerRepository) {


    @QueryHandler
    public Page handle(GetCustomerQuery getCustomerQuery){
        return null;
    }
}
