package ir.negah.bank.controller;

import ir.negah.bank.exception.OperationGeneralResponseDTO;
import ir.negah.bank.exception.RequestedNotFoundException;
import org.axonframework.axonserver.connector.query.AxonServerRemoteQueryHandlingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۱۰
 * TIME: ۱۴:۴۱
 */
@ControllerAdvice
public class CustomExceptionHandlers {

    @ExceptionHandler(AxonServerRemoteQueryHandlingException.class)
    public ResponseEntity<OperationGeneralResponseDTO> handleGeneralException(AxonServerRemoteQueryHandlingException e) {
        return new ResponseEntity<>(new OperationGeneralResponseDTO(e.getMessage(), "GET"), HttpStatus.NOT_FOUND);
    }
}
