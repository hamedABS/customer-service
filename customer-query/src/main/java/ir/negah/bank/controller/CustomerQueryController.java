package ir.negah.bank.controller;

import ir.negah.bank.query.GetCustomerQuery;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۶
 * TIME: ۱۷:۳۳
 */


@RestController
@RequestMapping("/customers/query")
public record CustomerQueryController(QueryGateway queryGateway) {

    public Page getCustomers(){
        GetCustomerQuery getCustomerQuery = new GetCustomerQuery();
//        queryGateway.query(getCustomerQuery,null);
        return null;
    }
}
