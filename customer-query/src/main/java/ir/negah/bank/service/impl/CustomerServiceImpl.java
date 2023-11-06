package ir.negah.bank.service.impl;

import ir.negah.bank.domain.dto.CustomerCreateRequestDTO;
import ir.negah.bank.repository.CustomerRepository;
import ir.negah.bank.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۴
 * TIME: ۱۲:۳۷
 */

@Service
public record CustomerServiceImpl(CustomerRepository customerRepository) implements CustomerService {

    @Override
    public Map<String, String> validateCustomer(CustomerCreateRequestDTO createRequestDTO) {
        Map<String, String> errors = new HashMap<>();
        customerRepository.findByEmailOrMobileNumberOrAccountNumber(createRequestDTO.getEmail(),
                        createRequestDTO.getMobileNumber(), createRequestDTO.getAccountNumber())
                .ifPresent(customerEntity -> {
                    if (customerEntity.getEmail().equals(createRequestDTO.getEmail())) {
                        errors.put("email", "Customer exists with given email");
                    }
                    if (customerEntity.getMobileNumber().equals(createRequestDTO.getMobileNumber())) {
                        errors.put("mobileNumber", "Customer exists with given mobile number");
                    }
                    if (customerEntity.getAccountNumber().equals(createRequestDTO.getAccountNumber())) {
                        errors.put("accountNumber", "This account number is assigned to the another customer");
                    }
                });

        //ToDo: check account existence
        //ToDo: check officeCode existence

        return errors;

    }
}
