package ir.negah.bank.domain.mapper;

import ir.negah.bank.domain.CustomerEntity;
import ir.negah.bank.domain.dto.CustomerFullDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۸
 * TIME: ۱۵:۲۸
 */

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerFullDTO entityToFullDTO(CustomerEntity customerEntity);

    List<CustomerFullDTO> listEntitiesToListCustomerFullDTOs(List<CustomerEntity> customerEntities);
}
