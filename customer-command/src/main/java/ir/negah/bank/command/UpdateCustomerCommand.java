package ir.negah.bank.command;

import ir.negah.bank.domain.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۱۰
 * TIME: ۱۶:۲۹
 */
@Data
@Builder
@AllArgsConstructor
public class UpdateCustomerCommand extends BaseCommand {

    private String accountNumber;

    private String officeCode;

    private String customerImage;

    private CustomerStatus customerStatus;

    private String firstname;

    private String lastname;

    private String fullName;

    private String displayName;

    private String mobileNo;

    private String email;

    private LocalDate dateOfBirth;
}
