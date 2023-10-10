package ir.negah.bank.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۱
 * TIME: ۱۱:۵۲
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseCommand {
    @TargetAggregateIdentifier
    private String aggregateId;
}
