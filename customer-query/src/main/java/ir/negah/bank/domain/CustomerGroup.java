package ir.negah.bank.domain;

import ir.negah.bank.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۱/۱۳
 * TIME: ۷:۵۷
 */


@Data
@Entity
@Table(name = "kar_customer_group")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerGroup extends BaseEntity {

    @Column(name = "activation_date", nullable = true)
    private LocalDate activationDate;

    @Column(name = "office_code", nullable = false)
    private String officeCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CustomerGroup parent;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private List<CustomerGroup> groupMembers = new ArrayList<>();

    @Column(name = "name", length = 100, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(name = "group_real_customer",
            joinColumns = @JoinColumn(name = "customer_group_id"),
            inverseJoinColumns = @JoinColumn(name = "real_customer_id"))
    private Set<RealCustomerEntity> realCustomerMembers = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "group_legal_customer",
            joinColumns = @JoinColumn(name = "customer_group_id"),
            inverseJoinColumns = @JoinColumn(name = "legal_customer_id"))
    private Set<RealCustomerEntity> legalCustomerMembers = new HashSet<>();
}
