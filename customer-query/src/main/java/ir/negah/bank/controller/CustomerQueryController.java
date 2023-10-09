package ir.negah.bank.controller;

import ir.negah.bank.domain.dto.CustomerCreateRequestDTO;
import ir.negah.bank.domain.dto.CustomerFullDTO;
import ir.negah.bank.query.GetAllCustomersQuery;
import ir.negah.bank.query.GetCustomerQuery;
import ir.negah.bank.service.CustomerService;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۶
 * TIME: ۱۷:۳۳
 */


@RestController
@RequestMapping("/customers")
public record CustomerQueryController(QueryGateway queryGateway,
                                      CustomerService customerService) {

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerFullDTO> getCustomer(@PathVariable(name = "customerId") Long customerId) {
        GetCustomerQuery getCustomerQuery = new GetCustomerQuery(customerId);
        CompletableFuture<CustomerFullDTO> query = queryGateway.query(getCustomerQuery, CustomerFullDTO.class);
        return ResponseEntity.ok(query.join());
    }

    @GetMapping
    public ResponseEntity<List<CustomerFullDTO>> getCustomers(@RequestParam(value = "firstname", required = false) String firstname,
                                                              @RequestParam(value = "lastname", required = false) String lastname,
                                                              @RequestParam(value = "createdDateFrom", required = false) @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDateTime createdDateFrom,
                                                              @RequestParam(value = "createdDateTo", required = false) @DateTimeFormat(pattern = "MM/dd/yyyy") LocalDateTime createdDateTo,
                                                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                              @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) throws ClassNotFoundException {
        GetAllCustomersQuery getAllCustomersQuery = new GetAllCustomersQuery(firstname,lastname,createdDateFrom,createdDateTo,page,size);
        CompletableFuture<List<CustomerFullDTO>> query = queryGateway.query(getAllCustomersQuery,
                ResponseTypes.multipleInstancesOf(CustomerFullDTO.class));
        return ResponseEntity.ok(query.join());
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
