package ir.negah.bank.controller;

import ir.negah.bank.command.CreateCustomerCommand;
import ir.negah.bank.domain.dto.CustomerCreateRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۷
 * TIME: ۱۰:۱۵
 */

@RestController
@RequestMapping("/api/customers/command")
public record CustomerCommandController(CommandGateway commandGateway, EventStore eventStore) {

    @PostMapping
    public String create(@RequestBody CustomerCreateRequestDTO requestDTO) {
        CreateCustomerCommand createCustomerCommand = CreateCustomerCommand
                .builder()
                .customerId(UUID.randomUUID().toString())
                .firstname(requestDTO.getFirstname())
                .lastname(requestDTO.getLastname())
                .fullName(requestDTO.getFullName())
                .displayName(requestDTO.getDisplayName())
                .email(requestDTO.getEmail())
                .accountNumber(requestDTO.getAccountNumber())
                .mobileNo(requestDTO.getMobileNo())
                .OfficeCode(requestDTO.getOfficeCode())
                .customerImage(requestDTO.getCustomerImage())
                .dateOfBirth(requestDTO.getDateOfBirth()).build();
        String result = commandGateway.sendAndWait(createCustomerCommand);
        return result;
    }

    @GetMapping(path = "/events/{aggregateId}")
    public Stream accountEvents(@PathVariable(name = "aggregateId") String aggregateId){
        return eventStore.readEvents(aggregateId).asStream();

    }
}
