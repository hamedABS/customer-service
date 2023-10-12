package ir.negah.bank.controller;

import ir.negah.bank.command.CreateCustomerCommand;
import ir.negah.bank.command.DeleteCustomerCommand;
import ir.negah.bank.command.DoOperationOnCustomerCommand;
import ir.negah.bank.command.UpdateCustomerCommand;
import ir.negah.bank.domain.Operation;
import ir.negah.bank.domain.dto.CustomerCreateRequestDTO;
import ir.negah.bank.domain.dto.CustomerModificationRequestDTO;
import ir.negah.bank.domain.mapper.CustomerMapper;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
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

    private final static CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @PostMapping
    public String create(@RequestBody CustomerCreateRequestDTO requestDTO) {
        CreateCustomerCommand createCustomerCommand = customerMapper.createRequestDTOToCreateCommand(requestDTO);

        createCustomerCommand.setAggregateId(UUID.randomUUID().toString());
        String result = commandGateway.sendAndWait(createCustomerCommand);
        return result;
    }

    @PutMapping("/{aggregateId}")
    public String update(@PathVariable(name = "aggregateId") String aggregateId,
                         @RequestBody CustomerModificationRequestDTO requestDTO) {
        UpdateCustomerCommand updateCustomerCommand = customerMapper.modificationRequestDTOToUpdateCommand(requestDTO);
        updateCustomerCommand.setAggregateId(aggregateId);
        return commandGateway.sendAndWait(updateCustomerCommand);
    }

    @GetMapping(path = "/events/{aggregateId}")
    public Stream accountEvents(@PathVariable(name = "aggregateId") String aggregateId) {
        return eventStore.readEvents(aggregateId).asStream();

    }

    @PostMapping("/{aggregateId}")
    public ResponseEntity<String> doCommand(@PathVariable(name = "aggregateId") String aggregateId,
                                            @RequestParam("command") Operation operation) throws Exception {
        String result = applyCommandOverCustomer(aggregateId, operation);
        return ResponseEntity.ok(result);

    }

    @DeleteMapping("/{aggregateId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(name = "aggregateId") String aggregateId){
        DeleteCustomerCommand command = new DeleteCustomerCommand();
        command.setAggregateId(aggregateId);
        String result = commandGateway.sendAndWait(command);
        return ResponseEntity.ok(result);
    }


    private String applyCommandOverCustomer(String aggregateId, Operation operation) throws Exception {
        DoOperationOnCustomerCommand doOperationOnCustomerCommand;
        String result;
        if (Arrays.asList(Operation.values()).contains(operation)) {
            doOperationOnCustomerCommand = new DoOperationOnCustomerCommand(aggregateId, operation);
            result = commandGateway.sendAndWait(doOperationOnCustomerCommand);
        } else {
            throw new Exception("command not found");
        }

        return result;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
