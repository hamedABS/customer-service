package ir.negah.bank.command;

import ir.negah.bank.domain.Operation;
import lombok.Getter;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۲
 * TIME: ۱۴:۳۰
 */

@Getter
public class DoOperationOnCustomerCommand extends BaseCommand {
    private Operation operation;

    public DoOperationOnCustomerCommand(String customerId, Operation operation) {
        super(customerId);
        this.operation = operation;
    }
}
