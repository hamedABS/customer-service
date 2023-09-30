package ir.negah.bank.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۷
 * TIME: ۱۰:۱۳
 */
@Data
@Builder
public class CreateCustomerCommand {

    @TargetAggregateIdentifier
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
