package ir.negah.bank.projection;

import ir.negah.bank.domain.CustomerEntity;
import ir.negah.bank.domain.CustomerStatus;
import ir.negah.bank.domain.dto.CustomerFullDTO;
import ir.negah.bank.domain.mapper.CustomerMapper;
import ir.negah.bank.exception.OperationGeneralResponseDTO;
import ir.negah.bank.exception.RequestedNotFoundException;
import ir.negah.bank.query.GetAllCustomersQuery;
import ir.negah.bank.query.GetCustomerByIdQuery;
import ir.negah.bank.repository.CustomerRepository;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۸
 * TIME: ۱۵:۲۴
 */
@Service
public class CustomerProjection {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    public CustomerProjection(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @QueryHandler
    public CustomerFullDTO handle(GetCustomerByIdQuery query) {
        //ToDo: handle not found exception ...
        Optional<CustomerEntity> byAggregateId = customerRepository.findByAggregateId(query.getAggregateId());
        byAggregateId.orElseThrow(() -> new RequestedNotFoundException(CustomerEntity.class.getSimpleName(), "GET", List.of("aggregate")));
        return customerMapper.entityToFullDTO(byAggregateId.get());
    }


    @QueryHandler
    public Page<CustomerFullDTO> handle(GetAllCustomersQuery query) {
        Pageable pageable = PageRequest.of(query.page(), query.size());
        Page<CustomerEntity> allCustomers = customerRepository.findAllByFirstnameLikeAndLastnameLikeAndCreatedDateIsBetween(
                query.firstname(),
                query.lastname(),
                query.createdDateFrom() == null ? LocalDateTime.of(1970, 1, 1,1,1,1) : query.createdDateFrom(),
                query.createdDateTo() == null ? LocalDateTime.now() : query.createdDateTo(),
                pageable);
        Page<CustomerFullDTO> result = allCustomers.map(customerEntity -> customerMapper.entityToFullDTO(customerEntity));

        return result;
    }

   /* @ExceptionHandler()
    public ResponseEntity<OperationGeneralResponseDTO> handleGeneralException(RequestedNotFoundException e) {
        return new ResponseEntity<>(new OperationGeneralResponseDTO(e.getMessage(), e.getOperation()), HttpStatus.NOT_FOUND);
    }*/
}
