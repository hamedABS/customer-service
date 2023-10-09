package ir.negah.bank.domain.dto;

import ir.negah.bank.domain.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۸
 * TIME: ۱۵:۲۲
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFullDTO {

    private String accountNumber;

    private String officeCode;

    private String customerImage;

    private CustomerStatus customerStatus;

    private String firstname;

    private String lastname;

    private String fullName;

    private String displayName;

    private String mobileNumber;

    private String email;

    private LocalDate dateOfBirth;
}
