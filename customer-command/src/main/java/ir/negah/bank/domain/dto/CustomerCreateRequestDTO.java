package ir.negah.bank.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

//    @Column(name = "client_status", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private ClientStatus clientStatus;

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
