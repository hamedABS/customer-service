package ir.negah.bank.controller;

import ir.negah.bank.domain.dto.CustomerCreateRequestDTO;
import ir.negah.bank.domain.dto.CustomerFullDTO;
import ir.negah.bank.exception.OperationGeneralResponseDTO;
import ir.negah.bank.exception.RequestedNotFoundException;
import ir.negah.bank.query.GetAllCustomersQuery;
import ir.negah.bank.query.GetCustomerByIdQuery;
import ir.negah.bank.service.CustomerService;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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

    @GetMapping("/{customerAggregateId}")
    public ResponseEntity<CustomerFullDTO> getCustomer(@PathVariable(name = "customerAggregateId") String customerAggregateId) {
        GetCustomerByIdQuery getCustomerByIdQuery = new GetCustomerByIdQuery(customerAggregateId);
        CompletableFuture<CustomerFullDTO> query = queryGateway.query(getCustomerByIdQuery, CustomerFullDTO.class);
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

    @GetMapping(value = "/{customerAggregateId}/watch",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CustomerFullDTO> subscribeToCustomer(@PathVariable(name = "customerAggregateId") String customerAggregateId){
        SubscriptionQueryResult<CustomerFullDTO, CustomerFullDTO> result = queryGateway.subscriptionQuery(new GetCustomerByIdQuery(customerAggregateId),
                ResponseTypes.instanceOf(CustomerFullDTO.class),
                ResponseTypes.instanceOf(CustomerFullDTO.class));

        return result.initialResult().concatWith(result.updates());
    }


    @ExceptionHandler(RequestedNotFoundException.class)
    public ResponseEntity<OperationGeneralResponseDTO> handleGeneralException(RequestedNotFoundException e) {
        return new ResponseEntity<>(new OperationGeneralResponseDTO(e.getMessage(), e.getOperation()), HttpStatus.NOT_FOUND);
    }
}
