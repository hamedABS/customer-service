package ir.negah.bank.service;

import ir.negah.bank.domain.dto.CustomerCreateRequestDTO;

import java.util.Map;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۴
 * TIME: ۱۲:۳۶
 */
public interface CustomerService {

    Map<String,String> validateCustomer(CustomerCreateRequestDTO createRequestDTO);
}
