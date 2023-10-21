package ir.negah.bank.domain.dto;

import ir.negah.bank.domain.Operation;

import java.time.LocalDateTime;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۲۱
 * TIME: ۱۰:۵۷
 */
public record DoOperationRequestDTO(Operation operation,
                                    LocalDateTime when) {
}
