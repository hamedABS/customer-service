package ir.negah.bank.controller;

import ir.negah.bank.command.ActivateCustomerCommand;
import ir.negah.bank.command.Command;
import ir.negah.bank.command.CreateCustomerCommand;
import ir.negah.bank.domain.dto.CustomerCreateRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۷
 * TIME: ۱۰:۱۵
 */

@RestController
@RequestMapping("/api/customers")
public record CustomerCommandController(CommandGateway commandGateway, EventStore eventStore) {

    @PostMapping
    public String create(@RequestBody CustomerCreateRequestDTO requestDTO) {
        CreateCustomerCommand createCustomerCommand = CreateCustomerCommand
                .builder()
                .firstname(requestDTO.getFirstname())
                .lastname(requestDTO.getLastname())
                .fullName(requestDTO.getFullName())
                .displayName(requestDTO.getDisplayName())
                .email(requestDTO.getEmail())
                .accountNumber(requestDTO.getAccountNumber())
                .customerStatus(requestDTO.getCustomerStatus())
                .mobileNo(requestDTO.getMobileNo())
                .OfficeCode(requestDTO.getOfficeCode())
                .customerImage(requestDTO.getCustomerImage())
                .dateOfBirth(requestDTO.getDateOfBirth()).build();

        createCustomerCommand.setCustomerId(UUID.randomUUID().toString());
        String result = commandGateway.sendAndWait(createCustomerCommand);
        return result;
    }

    @GetMapping(path = "/events/{aggregateId}")
    public Stream accountEvents(@PathVariable(name = "aggregateId") String aggregateId) {
        return eventStore.readEvents(aggregateId).asStream();

    }

    @PostMapping("/{customerId}")
    public ResponseEntity<String> doCommand(@PathVariable(name = "customerId") String customerId,
                                            @RequestParam("command") Command command) throws Exception {
        String result = applyCommandOverCustomer(customerId, command);
        return ResponseEntity.ok(result);

    }

    private String applyCommandOverCustomer(String customerId, Command command) throws Exception {
        ActivateCustomerCommand activateCustomerCommand;
        String result;

        if (command.equals(Command.ACTIVATE)) {
            activateCustomerCommand = new ActivateCustomerCommand(customerId, command);
            result = commandGateway.sendAndWait(activateCustomerCommand);
        } else {
            throw new Exception("customerNotFound");
        }

        return result;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
