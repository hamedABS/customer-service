package ir.negah.bank.command;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۲
 * TIME: ۱۴:۳۰
 */

public class ActivateCustomerCommand extends BaseCommand{
    public ActivateCustomerCommand(String customerId, Command command) {
        super(customerId, command);
    }
}
