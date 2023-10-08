package ir.negah.bank.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۳۰
 * TIME: ۱۷:۴۸
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerActivatedEvent {
    private String customerId;
    private String email;
}
