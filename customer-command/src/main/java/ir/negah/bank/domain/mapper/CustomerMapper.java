package ir.negah.bank.domain.mapper;

import ir.negah.bank.command.CreateCustomerCommand;
import ir.negah.bank.command.UpdateCustomerCommand;
import ir.negah.bank.domain.dto.CustomerCreateRequestDTO;
import ir.negah.bank.domain.dto.CustomerModificationRequestDTO;
import ir.negah.bank.events.CustomerCreatedEvent;
import ir.negah.bank.events.CustomerModifiedEvent;
import org.mapstruct.Mapper;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۴
 * TIME: ۱۱:۳۲
 */

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CreateCustomerCommand createRequestDTOToCreateCommand(CustomerCreateRequestDTO requestDTO);
    CustomerCreatedEvent createCommandToCreatedEvent(CreateCustomerCommand command);

    UpdateCustomerCommand modificationRequestDTOToUpdateCommand(CustomerModificationRequestDTO requestDTO);

    CustomerModifiedEvent updateCommandToModifiedEvent(UpdateCustomerCommand command);
}
