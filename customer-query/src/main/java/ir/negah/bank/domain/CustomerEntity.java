package ir.negah.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۵
 * TIME: ۱۰:۰۲
 */


@Data
@Entity
@Table(name = "CUSTOMER")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity extends BaseEntity {

    @Column(name = "aggregate_id", unique = true)
    private String aggregateId;

    @Column(name = "account_no", length = 20, unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "office_code", nullable = false)
    private String officeCode;

    @Column(name = "customer_image",nullable = false)
    private String customerImage;

    @Column(name = "sign_image",nullable = false)
    private String signImage;

    @Column(name = "client_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;

    @Column(name = "firstname", length = 50)
    private String firstname;

    @Column(name = "english_firstname", length = 50)
    private String englishFirstname;

    @Column(name = "lastname", length = 50)
    private String lastname;

    @Column(name = "english_lastname", length = 50)
    private String englishLastname;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "display_name", length = 100)
    private String displayName;

    @Column(name = "national_code", length = 10, unique = true)
    private String nationalCode;

    @Column(name = "birth_certificate_number", unique = true)
    private String birtCertificateNumber;

    @Column(name = "passport_number", unique = true)
    private String passport;

    @Column(name = "birth_certificate_serial", unique = true)
    private String birthCertificateSerial;

    @Column(name = "duplicate_birth_certificate_serial", unique = true)
    private String duplicateBirthCertificateSerial;

    @Column(name = "birth_certificate_series", unique = true)
    private String birthCertificateSeries;

    @Column(name = "birth_certificate_char_series", unique = true)
    private String birthCertificateCharSeries;

    @Column(name = "business")
    private String business;

    @Column(name = "mobile_number", length = 50, unique = true)
    private String mobileNumber;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "date_of_issuance")
    private LocalDate dateOfIssuance;

    @Column(name = "country_of_birth")
    private String countryOfBirth;

    @Column(name = "placeOfBirth")
    private String placeOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_type_id")
    private CodeValue occupationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_id")
    private CodeValue occupation;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id")
    private CodeValue gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_classification_id")
    private CodeValue customerClassification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_type_id")
    private CodeValue customerType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marital_status_id")
    private CodeValue maritalStatus;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Address> addresses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<OperationDoneByWhenWhy> operations;

}
