package ir.negah.bank.command;

import ir.negah.bank.domain.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۷
 * TIME: ۱۰:۱۳
 */
@Data
@Builder
@AllArgsConstructor
public class CreateCustomerCommand extends BaseCommand {

    private String accountNumber;

    private String officeCode;

    private String customerImage;

    private String signImage;

    private CustomerStatus customerStatus;

    private String firstname;

    private String lastname;

    private String fullName;

    private String displayName;

    private String mobileNo;

    private String email;

    private LocalDate dateOfBirth;
}
