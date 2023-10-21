package ir.negah.bank.repository;

import ir.negah.bank.domain.OperationDoneByWhenWhy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۲۱
 * TIME: ۱۰:۵۱
 */
@Repository
public interface OperationDoneByWhenWhyRepository extends JpaRepository<OperationDoneByWhenWhy,Long> {
}
