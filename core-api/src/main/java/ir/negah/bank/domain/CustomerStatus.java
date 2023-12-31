package ir.negah.bank.domain;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۲۵
 * TIME: ۱۰:۲۰
 */

public enum CustomerStatus {
    INVALID,
    PENDING,
    ACTIVE,
    TRANSFER_IN_PROGRESS,
    TRANSFER_ON_HOLD,
    CLOSED,
    REJECTED,
    WITHDRAWN,
    MOBILE_VERIFIED,
    MOBILE_REJECTED;
}
