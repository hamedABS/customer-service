package ir.negah.bank.events;

import ir.negah.bank.domain.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۳۰
 * TIME: ۱۷:۴۸
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoOperationOnCustomerEvent extends BaseEvent{
    private Operation operation;
    private LocalDateTime when;
}
