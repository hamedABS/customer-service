package ir.negah.bank.query;

import ir.negah.bank.domain.CustomerStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۳۰
 * TIME: ۹:۴۴
 */

@ToString
@EqualsAndHashCode
@Setter
@Getter
public final class GetAllCustomersQuery {
    private String firstname;
    private String lastname;
    private String officeCode;
    private CustomerStatus customerStatus;
    private String nationalCode;
    private String birtCertificateNumber;
    private String business;
    private String mobileNumber;
    private String email;
    private String gender;
    private String placeOfBirth;
    private String occupationType;
    private String occupation;
    private String customerType;
    private String maritalStatus;
    private String customerClassification;
    private LocalDate dateOfBirthFrom;
    private LocalDate dateOfBirthTo;
    private LocalDateTime createdDateTimeFrom;
    private LocalDateTime createdDateTimeTo;
    private Integer page;
    private Integer size;

    /**
     *
     */
    public GetAllCustomersQuery(String firstname, String lastname, String officeCode, CustomerStatus customerStatus,
                                String nationalCode, String birtCertificateNumber, String business, String mobileNumber,
                                String email, String placeOfBirth, String occupationType, String occupation,
                                String gender, String customerType, String maritalStatus, String customerClassification,
                                LocalDate dateOfBirthFrom, LocalDate dateOfBirthTo, LocalDateTime createdDateTimeFrom,
                                LocalDateTime createdDateTimeTo, Integer page, Integer size) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.officeCode = officeCode;
        this.customerStatus = customerStatus;
        this.nationalCode = nationalCode;
        this.birtCertificateNumber = birtCertificateNumber;
        this.business = business;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.gender = gender;
        this.placeOfBirth = placeOfBirth;
        this.occupationType = occupationType;
        this.occupation = occupation;
        this.maritalStatus = maritalStatus;
        this.customerType = customerType;
        this.customerClassification = customerClassification;
        this.dateOfBirthFrom = dateOfBirthFrom;
        this.dateOfBirthTo = dateOfBirthTo;
        this.createdDateTimeFrom = createdDateTimeFrom;
        this.createdDateTimeTo = createdDateTimeTo;
        this.page = page;
        this.size = size;
    }
}
