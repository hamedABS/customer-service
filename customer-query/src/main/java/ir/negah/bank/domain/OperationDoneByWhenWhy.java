package ir.negah.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۵
 * TIME: ۱۴:۱۸
 */
@Data
@Entity
@Table(name = "OPERATION_DONE_BY_AND_WHEN")
@AllArgsConstructor
@NoArgsConstructor
public class OperationDoneByWhenWhy extends BaseEntity {

    @Column(name = "operation")
    @Enumerated(EnumType.STRING)
    private Operation operation;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "by")
    private String by;

    @ManyToOne
    @JoinColumn(name = "why_id")
    private CodeValue why;
}
