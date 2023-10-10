package ir.negah.bank.repository;

import ir.negah.bank.domain.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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



    Optional<CustomerEntity> findByEmailOrMobileNumber(String email,String mobileNumber);

    @Query("select c from CustomerEntity c where (:firstname is null or c.firstname like concat('%',:firstname,'%'))" +
            " and (:lastname is null or c.lastname like concat('%',:lastname,'%'))"+
            " and c.createdDate between :fromDate and :toDate order by c.createdDate")
    Page<CustomerEntity> findAllByFirstnameLikeAndLastnameLikeAndCreatedDateIsBetween(String firstname, String lastname,
                                                                                      LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

    Optional<CustomerEntity> findByEmailOrMobileNumberOrAccountNumber(String email,String mobileNumber,String accountNumber);
}
