package ir.negah.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۱۲
 * TIME: ۱۴:۱۷
 */

@Data
@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseEntity {

    @Column(name = "street")
    private String street;

    @Column(name = "street2")
    private String street2;

    @Column(name = "alley")
    private String alley;

    @Column(name = "plaque_number")
    private Integer plaque_number;

    @Column(name = "floor_number")
    private Integer floor_number;

    @Column(name = "unit_number")
    private Integer unit_number;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "phone_number", length = 11, unique = true)
    private String phoneNumber;

    @Column(name = "address_detail")
    private String addressDetail;

    @ManyToOne
    private CustomerEntity customer;

}
