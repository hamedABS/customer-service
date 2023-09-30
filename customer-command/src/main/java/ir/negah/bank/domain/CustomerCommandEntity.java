package ir.negah.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

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
 * DATE: ۲۰۲۳/۹/۲۷
 * TIME: ۹:۰۵
 */

@Data
@Entity
@Table(name = "CUSTOMER_COMMAND")
@Audited
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCommandEntity extends BaseEntity {

    @Column(name = "account_no", length = 20, unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "office_code", nullable = false)
    private String OfficeCode;

    @Column(name = "customer_image",nullable = false)
    private String customerImage;

//    @Column(name = "client_status", nullable = false)
//    @Enumerated(EnumType.STRING)
//    private ClientStatus clientStatus;

    @Column(name = "firstname", length = 50)
    private String firstname;

    @Column(name = "lastname", length = 50)
    private String lastname;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "display_name", length = 100)
    private String displayName;

    @Column(name = "mobile_no", length = 50, unique = true)
    private String mobileNo;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "gender_id")
//    private CodeValue gender;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "client_classification_id")
//    private CodeValue clientClassification;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "client_type_id")
//    private CodeValue clientType;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    private List<OperationDoneByWhenWhy> operations;

}
