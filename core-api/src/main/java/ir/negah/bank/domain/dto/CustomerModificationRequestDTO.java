package ir.negah.bank.domain.dto;

import ir.negah.bank.domain.CustomerStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۱۰
 * TIME: ۱۶:۲۱
 */
@Data
public class CustomerModificationRequestDTO {

    @NotBlank(message = "accountNumber could not be null or empty")
    private String accountNumber;

    @NotBlank(message = "officeCode could not be null or empty")
    private String officeCode;

    @NotBlank(message = "customerImage could not be null or empty")
    private String customerImage;

    @NotBlank(message = "customerStatus could not be null or empty")
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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "MM/dd/yyyy")
    private LocalDate dateOfBirth;
}
