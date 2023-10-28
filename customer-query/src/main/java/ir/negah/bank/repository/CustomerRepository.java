package ir.negah.bank.repository;

import ir.negah.bank.domain.CustomerEntity;
import ir.negah.bank.domain.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۳۰
 * TIME: ۹:۴۷
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByEmail(String email);

    Optional<CustomerEntity> findByAggregateId(String aggregateId);


    Optional<CustomerEntity> findByEmailOrMobileNumber(String email, String mobileNumber);

    @Query("select c from CustomerEntity c" +
            " left join CodeValue gender on gender.id = c.gender.id"+
            " left join CodeValue occupationType on occupationType.id = c.occupationType.id"+
            " left join CodeValue occupation on occupation.id = c.occupation.id"+
            " left join CodeValue maritalStatus on maritalStatus.id = c.maritalStatus.id"+
            " left join CodeValue customerType on customerType.id = c.customerType.id"+
            " left join CodeValue customerClassification on customerClassification.id = c.customerClassification.id"+
            " where (:firstname is null or c.firstname like concat('%',:firstname,'%'))" +
            " and (:lastname is null or c.lastname like concat('%',:lastname,'%'))" +
            " and (:officeCode is null or c.officeCode = :officeCode)" +
            " and (:customerStatus is null or c.customerStatus = :customerStatus)" +
            " and (:nationalCode is null or c.nationalCode = :nationalCode)" +
            " and (:birtCertificateNumber is null or c.birtCertificateNumber = :birtCertificateNumber)" +
            " and (:business is null or c.business = :business)" +
            " and (:mobileNumber is null or c.mobileNumber = :mobileNumber)" +
            " and (:email is null or c.email like concat('%',:email,'%'))" +
            " and (:gender is null or gender.label = :gender)" +
            " and (:placeOfBirth is null or c.placeOfBirth = :placeOfBirth)" +
            " and (:occupationType is null or occupationType.label = :occupationType)" +
            " and (:occupation is null or occupation.label = :occupation)" +
            " and (:maritalStatus is null or maritalStatus.label = :maritalStatus)" +
            " and (:customerType is null or customerType.label = :customerType)" +
            " and (:customerClassification is null or customerClassification.label = :customerClassification)" +
            " and c.dateOfBirth between :dateOfBirthFrom and :dateOfBirthTo" +
            " and c.createdDate between :fromDate and :toDate order by c.createdDate"
    )
    Page<CustomerEntity> findAllByFirstnameLikeAndLastnameLikeAndCreatedDateIsBetweenAndOtherFilters(String firstname, String lastname,
                                                                                                     String officeCode, CustomerStatus customerStatus,
                                                                                                     String nationalCode, String birtCertificateNumber,
                                                                                                     String business, String mobileNumber, String email,
                                                                                                     String gender, String placeOfBirth, String occupationType,
                                                                                                     String occupation, String maritalStatus, String customerType,
                                                                                                     String customerClassification, LocalDate dateOfBirthFrom,
                                                                                                     LocalDate dateOfBirthTo, LocalDateTime fromDate,
                                                                                                     LocalDateTime toDate, Pageable pageable);

    Optional<CustomerEntity> findByEmailOrMobileNumberOrAccountNumber(String email, String mobileNumber, String accountNumber);
}
