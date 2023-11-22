package ir.negah.bank.domain.dto;

import ir.negah.bank.domain.CustomerStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۷
 * TIME: ۱۰:۱۷
 */
@Data
public class CustomerCreateRequestDTO {

    @NotBlank(message = "accountNumber could not be null or empty")
    private String accountNumber;

    @NotBlank(message = "officeCode could not be null or empty")
    private String officeCode;

    @NotBlank(message = "customerImage could not be null or empty")
    private String customerImage;

    @NotBlank(message = "signImage could not be null or empty")
    private String signImage;

//    @NotBlank(message = "customerStatus could not be null or empty")
    private CustomerStatus customerStatus;

    @NotBlank(message = "firstname could not be null or empty")
    private String firstname;

    @NotBlank(message = "lastname could not be null or empty")
    private String lastname;

    @NotBlank(message = "fullName could not be null or empty")
    private String fullName;

    private String displayName;

    @NotBlank(message = "mobileNo could not be null or empty")
    private String mobileNumber;

    @NotBlank(message = "email could not be null or empty")
    private String email;

    @NotBlank(message = "nationalCode could not be zero or empty")
    private String nationalCode;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "MM/dd/yyyy")
    private LocalDate dateOfBirth;

    private String postalCode;

    private String cardSerialNo;
}
