package ir.negah.bank.events;

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
public class CustomerCreatedEvent {


    private String customerId;

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

    private LocalDate dateOfBirth;

}
