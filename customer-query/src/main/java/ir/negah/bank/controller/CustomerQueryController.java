package ir.negah.bank.controller;

import ir.negah.bank.domain.dto.CustomerCreateRequestDTO;
import ir.negah.bank.query.GetCustomerQuery;
import ir.negah.bank.service.CustomerService;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۶
 * TIME: ۱۷:۳۳
 */


@RestController
@RequestMapping("/customers")
public record CustomerQueryController(QueryGateway queryGateway,
                                      CustomerService customerService) {

    public Page getCustomers() {
        GetCustomerQuery getCustomerQuery = new GetCustomerQuery();
//        queryGateway.query(getCustomerQuery,null);
        return null;
    }


    @PostMapping("/validate")
    ResponseEntity<Map<String, String>> validateCustomer(@RequestBody @Valid CustomerCreateRequestDTO createRequestDTO) {
        Map<String, String> errors = customerService.validateCustomer(createRequestDTO);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.noContent().build();
    }

}
