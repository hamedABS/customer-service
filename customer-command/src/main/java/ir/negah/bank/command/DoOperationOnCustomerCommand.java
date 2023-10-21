package ir.negah.bank.command;

import ir.negah.bank.domain.Operation;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۲
 * TIME: ۱۴:۳۰
 */

@Getter
public class DoOperationOnCustomerCommand extends BaseCommand {
    private final Operation operation;
    private final LocalDateTime when;

    public DoOperationOnCustomerCommand(String customerId, Operation operation,LocalDateTime when) {
        super(customerId);
        this.operation = operation;
        this.when = when;
    }
}
