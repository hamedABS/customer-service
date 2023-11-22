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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۱/۱۵
 * TIME: ۱۷:۰۴
 */

@Data
@Entity
@Table(name = "kar_real_customer")
@AllArgsConstructor
@NoArgsConstructor
public class RealCustomerEntity extends BaseEntity {


    @Column(name = "aggregate_id", unique = true)
    private String aggregateId;

    @Column(name = "customer_id", unique = true)
    private String customerId;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "birth_certificate_number", unique = true)
    private String birtCertificateNumber;

    @Column(name = "birth_certificate_serial", unique = true)
    private String birthCertificateSerial;

    @Column(name = "birth_certificate_series", unique = true)
    private String birthCertificateSeries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birth_certificate_type_id")
    private CodeValue birthCertificateType;

    @Column(name = "office_code", nullable = false)
    private String officeCode;

    @Column(name = "account_no", length = 20, unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "shahab")
    private String shahab;

    @Column(name = "customer_image", nullable = false)
    private String customerImage;

    @Column(name = "sign_image", nullable = false)
    private String signImage;

    @Column(name = "client_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;

    @Column(name = "firstname_latin")
    private String firstnameLatin;

    @Column(name = "lastname_latin")
    private String lastnameLatin;

    @Column(name = "father_name_latin")
    private String fatherNameLatin;

    @Column(name = "national_code", length = 10, unique = true)
    private String nationalCode;

    @Column(name = "passport_number", unique = true)
    private String passport;

    @Column(name = "business")
    private String business;

    @Column(name = "mobile_number", length = 50, unique = true)
    private String mobileNumber;

    @Column(name = "telephone_number", length = 50, unique = true)
    private String telephoneNumber;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "date_of_issuance")
    private LocalDate dateOfIssuance;

    @Column(name = "country_of_birth")
    private String countryOfBirth;

    @Column(name = "national")
    private String national;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "place_of_issuance")
    private String placeOfIssuance;

    @Column(name = "issuance_office_code", nullable = false)
    private String issuanceOfficeCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "live_status_id")
    private CodeValue liveStatus;

    @Column(name = "death_date")
    private LocalDate deathDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_type_id")
    private CodeValue occupationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_id")
    private CodeValue occupation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residency_id")
    private CodeValue residency;

    @Column(name = "residency_from")
    private LocalDate residencyFrom;

    @Column(name = "residency_to")
    private LocalDate residencyTo;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id")
    private CodeValue gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_doc_id")
    private CodeValue fDoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "degree_id")
    private CodeValue degree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "religion_id")
    private CodeValue religion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surname_id")
    private CodeValue surname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marital_status_id")
    private CodeValue maritalStatus;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Address> addresses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<OperationDoneByWhenWhy> operations;

    @ManyToMany(mappedBy = "realCustomerMembers")
    private List<CustomerGroup> groups;
}
