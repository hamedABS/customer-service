package ir.negah.bank.repository;

import ir.negah.bank.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۳۰
 * TIME: ۹:۴۷
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByEmail(String email);

    Optional<CustomerEntity> findByEmailOrMobileNumber(String email,String mobileNumber);

    Optional<CustomerEntity> findByEmailOrMobileNumberOrAccountNumber(String email,String mobileNumber,String accountNumber);
}
