package ir.negah.bank.projection;

import ir.negah.bank.domain.CustomerEntity;
import ir.negah.bank.domain.dto.CustomerFullDTO;
import ir.negah.bank.domain.mapper.CustomerMapper;
import ir.negah.bank.exception.RequestedNotFoundException;
import ir.negah.bank.query.GetAllCustomersQuery;
import ir.negah.bank.query.GetCustomerByIdQuery;
import ir.negah.bank.repository.CustomerRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize());
        Page<CustomerEntity> allCustomers = customerRepository.findAllByFirstnameLikeAndLastnameLikeAndCreatedDateIsBetweenAndOtherFilters(
                query.getFirstname(),
                query.getLastname(),
                query.getOfficeCode(),
                query.getCustomerStatus(),
                query.getNationalCode(),
                query.getBirtCertificateNumber(),
                query.getBusiness(),
                query.getMobileNumber(),
                query.getEmail(),
                query.getGender(),
                query.getPlaceOfBirth(),
                query.getOccupationType(),
                query.getOccupation(),
                query.getMaritalStatus(),
                query.getCustomerType(),
                query.getCustomerClassification(),
                query.getDateOfBirthFrom() == null ? LocalDate.of(1970, 1, 11) : query.getDateOfBirthFrom(),
                query.getDateOfBirthTo() == null ? LocalDate.now() : query.getDateOfBirthTo(),
                query.getCreatedDateTimeFrom() == null ? LocalDateTime.of(1970, 1, 1, 1, 1, 1) : query.getCreatedDateTimeFrom(),
                query.getCreatedDateTimeTo() == null ? LocalDateTime.now() : query.getCreatedDateTimeTo(),
                pageable);

        return allCustomers.map(customerEntity -> customerMapper.entityToFullDTO(customerEntity));
    }

   /* @ExceptionHandler()
    public ResponseEntity<OperationGeneralResponseDTO> handleGeneralException(RequestedNotFoundException e) {
        return new ResponseEntity<>(new OperationGeneralResponseDTO(e.getMessage(), e.getOperation()), HttpStatus.NOT_FOUND);
    }*/
}
