package ir.negah.bank.service;

import ir.negah.bank.domain.CustomerEntity;
import ir.negah.bank.domain.dto.CustomerFullDTO;
import ir.negah.bank.domain.mapper.CustomerMapper;
import ir.negah.bank.query.GetAllCustomersQuery;
import ir.negah.bank.query.GetCustomerQuery;
import ir.negah.bank.repository.CustomerRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۸
 * TIME: ۱۵:۲۴
 */
@Service
public class QueryHandlerService {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    public QueryHandlerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @QueryHandler
    public CustomerFullDTO handle(GetCustomerQuery query) {
        Optional<CustomerEntity> byId = customerRepository.findById(query.getCustomerId());
        return customerMapper.entityToFullDTO(byId.get());
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
}
