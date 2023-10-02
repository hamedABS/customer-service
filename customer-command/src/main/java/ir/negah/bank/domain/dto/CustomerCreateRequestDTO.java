package ir.negah.bank.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.negah.bank.domain.CustomerStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۷
 * TIME: ۱۰:۱۷
 */
@Data
public class CustomerCreateRequestDTO {

    private String accountNumber;

    private String OfficeCode;

    private String customerImage;

    private CustomerStatus customerStatus;

    private String firstname;

    private String lastname;

    private String fullName;

    private String displayName;

    private String mobileNo;

    private String email;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate dateOfBirth;
}
