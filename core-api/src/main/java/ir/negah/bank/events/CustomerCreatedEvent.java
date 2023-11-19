package ir.negah.bank.events;

import ir.negah.bank.domain.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۷
 * TIME: ۱۰:۳۰
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCreatedEvent extends BaseEvent{


    private String accountNumber;

    private String officeCode;

    private String customerImage;

    private String signImage;

    private CustomerStatus customerStatus;

    private String firstname;

    private String lastname;

    private String fullName;

    private String displayName;

    private String mobileNumber;

    private String email;

    private String nationalCode;

    private LocalDate dateOfBirth;

}
