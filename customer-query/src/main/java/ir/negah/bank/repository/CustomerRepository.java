package ir.negah.bank.repository;

import ir.negah.bank.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۳۰
 * TIME: ۹:۴۷
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
