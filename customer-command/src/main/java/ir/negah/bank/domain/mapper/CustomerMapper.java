package ir.negah.bank.domain.mapper;

import ir.negah.bank.command.CreateCustomerCommand;
import ir.negah.bank.domain.dto.CustomerCreateRequestDTO;
import ir.negah.bank.events.CustomerCreatedEvent;
import org.mapstruct.Mapper;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۴
 * TIME: ۱۱:۳۲
 */

@Mapper
public interface CustomerMapper {
    CreateCustomerCommand createRequestDTOToCommandTO(CustomerCreateRequestDTO requestDTO);
    CustomerCreatedEvent createCommandToCreateCommand(CreateCustomerCommand command);

}
