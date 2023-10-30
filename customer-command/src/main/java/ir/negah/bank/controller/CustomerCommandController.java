package ir.negah.bank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.negah.bank.clients.centralBank.domain.ChequeRequestDTO;
import ir.negah.bank.clients.civilRegistry.domain.CivilRegistrationResponseDTO;
import ir.negah.bank.clients.civilRegistry.domain.ShahkarRequestDTO;
import ir.negah.bank.clients.newsPaper.domain.NewsRequestDTO;
import ir.negah.bank.command.CreateCustomerCommand;
import ir.negah.bank.command.DeleteCustomerCommand;
import ir.negah.bank.command.DoOperationOnCustomerCommand;
import ir.negah.bank.command.UpdateCustomerCommand;
import ir.negah.bank.domain.Operation;
import ir.negah.bank.domain.dto.CustomerCreateRequestDTO;
import ir.negah.bank.domain.dto.CustomerModificationRequestDTO;
import ir.negah.bank.domain.dto.DoOperationRequestDTO;
import ir.negah.bank.domain.mapper.CustomerMapper;
import ir.negah.bank.exception.MobileVerificationMismatchException;
import ir.negah.bank.service.CustomerService;
import ir.negah.bank.util.com.github.eloyzone.jalalicalendar.DateConverter;
import ir.negah.bank.util.com.github.eloyzone.jalalicalendar.JalaliDate;
import ir.negah.bank.util.com.github.eloyzone.jalalicalendar.JalaliDateFormatter;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
@Slf4j
public record CustomerCommandController(CommandGateway commandGateway, EventStore eventStore,
                                        CustomerService customerService) {

    private final static CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @PostMapping
    public String create(@RequestBody CustomerCreateRequestDTO requestDTO) throws JsonProcessingException {
        ShahkarRequestDTO shahkarRequestDTO = new ShahkarRequestDTO(requestDTO.getNationalCode(), 0, requestDTO.getMobileNumber());
        ChequeRequestDTO chequeRequestDTO = new ChequeRequestDTO(1, "1378125411");
        NewsRequestDTO newsRequestDTO = new NewsRequestDTO("0", "123456789");
        customerService.mobileVerification(shahkarRequestDTO);
        customerService.estelam(chequeRequestDTO);
        DateConverter dateConverter = new DateConverter();
        JalaliDate jalaliDate2 = dateConverter.gregorianToJalali(requestDTO.getDateOfBirth().getYear(), requestDTO.getDateOfBirth().getMonthValue(), requestDTO.getDateOfBirth().getDayOfMonth());
        String[] split = jalaliDate2.toString().split("-");
        if (split[1].length() == 1) {
            split[1] = "0".concat(split[1]);
        }
        if (split[2].length() == 1) {
            split[2] = "0".concat(split[2]);
        }
        String joinedJalaliDate = String.join("", split[0], split[1], split[2]);
//        String result2 = jalaliDate2.format(new JalaliDateFormatter("yyyyMMdd", JalaliDateFormatter.FORMAT_IN_PERSIAN));

        CivilRegistrationResponseDTO civilRegistrationResponseDTO =customerService.getPersonalInfo(Long.parseLong(joinedJalaliDate), requestDTO.getNationalCode());
        customerService.getPhotoByNationalCode(requestDTO.getCardSerialNo(), requestDTO.getNationalCode());
        customerService.getAddress(requestDTO.getPostalCode());
        customerService.getNews(newsRequestDTO);
        CreateCustomerCommand createCustomerCommand = customerMapper.createRequestDTOToCreateCommand(requestDTO);
        log.info(CustomerCreateRequestDTO.class.getSimpleName() + " Processing ...");
        createCustomerCommand.setAggregateId(UUID.randomUUID().toString());
        return commandGateway.sendAndWait(createCustomerCommand);

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
                                            @RequestBody DoOperationRequestDTO doOperationRequestDTO) throws Exception {
        String result = applyCommandOverCustomer(aggregateId, doOperationRequestDTO.operation(), doOperationRequestDTO.when());
        return ResponseEntity.ok(result);

    }

    @DeleteMapping("/{aggregateId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(name = "aggregateId") String aggregateId) {
        DeleteCustomerCommand command = new DeleteCustomerCommand();
        command.setAggregateId(aggregateId);
        String result = commandGateway.sendAndWait(command);
        return ResponseEntity.ok(result);
    }


    private String applyCommandOverCustomer(String aggregateId, Operation operation, LocalDateTime when) throws Exception {
        DoOperationOnCustomerCommand doOperationOnCustomerCommand;
        String result;
        if (Arrays.asList(Operation.values()).contains(operation)) {
            doOperationOnCustomerCommand = new DoOperationOnCustomerCommand(aggregateId, operation, when);
            result = commandGateway.sendAndWait(doOperationOnCustomerCommand);
        } else {
            throw new Exception("command not found");
        }

        return result;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception e) {
        ResponseEntity responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }

    @ExceptionHandler(MobileVerificationMismatchException.class)
    public ResponseEntity exceptionHandler(MobileVerificationMismatchException e) {
        ResponseEntity responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }
}
