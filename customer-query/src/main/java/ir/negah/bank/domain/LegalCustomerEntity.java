
package ir.negah.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۱/۱۱
 * TIME: ۱۷:۰۷
 */

@Data
@Entity
@Table(name = "kar_legal_customer")
@AllArgsConstructor
@NoArgsConstructor
public class LegalCustomerEntity extends BaseEntity {

    @Column(name = "customer_id", unique = true )
    private String customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "latin_name")
    private String latinName;

    @Column(name = "worker_count")
    private Integer workerCount;

    @Column(name = "office_code", nullable = false)
    private String officeCode;;

    @Column(name = "register_number")
    private String registerNumber;

    @Column(name = "national_id", unique = true)
    private String nationalId;

    @Column(name = "registered_city")
    private String registeredCity;

    @Column(name = "registered_date")
    private LocalDate registeredDate;

    @Column(name = "fund")
    private Integer fund;

    @Column(name = "property")
    private Integer property;

    @Column(name = "economical_code")
    private String economicalCode;

    @Column(name = "county")
    private String country;

    @Column(name = "register_country")
    private String registerCountry;

    @Column(name = "telephone_number", length = 50, unique = true)
    private String telephoneNumber;

    @Column(name = "mobile_number", length = 50, unique = true)
    private String mobileNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residency_id")
    private CodeValue residency;

    @Column(name = "shahab")
    private String shahab;

    @Column(name = "agahi_number")
    private String agahiNumber;

    @Column(name = "rooznameh_Number")
    private String rooznamehNumber;

    @Column(name = "agahi_date")
    private LocalDate agahiDate;

    @Column(name = "rooznameh_date")
    private LocalDate rooznamehDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_type_id")
    private CodeValue institutionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register_unit_id")
    private CodeValue registerUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "economical_section_id")
    private CodeValue economicalSection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_group_id")
    private CodeValue customerGroup;

    @ManyToMany(mappedBy = "legalCustomerMembers")
    private List<CustomerGroup> groups;








}
