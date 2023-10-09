package ir.negah.bank.query;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۹/۳۰
 * TIME: ۹:۴۴
 */

public final class GetAllCustomersQuery {
    private String firstname;
    private String lastname;
    private LocalDateTime createdDateTimeFrom;
    private LocalDateTime createdDateTimeTo;
    private Integer page;
    private Integer size;

    /**
     */
    public GetAllCustomersQuery(String firstname, String lastname, LocalDateTime createdDateTimeFrom,
                                LocalDateTime createdDateTimeTo, Integer page, Integer size) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.createdDateTimeFrom = createdDateTimeFrom;
        this.createdDateTimeTo = createdDateTimeTo;
        this.page = page;
        this.size = size;
    }

    public String firstname() {
        return firstname;
    }

    public String lastname() {
        return lastname;
    }

    public LocalDateTime createdDateFrom() {
        return createdDateTimeFrom;
    }

    public LocalDateTime createdDateTo() {
        return createdDateTimeTo;
    }

    public Integer page() {
        return page;
    }

    public Integer size() {
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GetAllCustomersQuery) obj;
        return Objects.equals(this.firstname, that.firstname) &&
                Objects.equals(this.lastname, that.lastname) &&
                Objects.equals(this.createdDateTimeFrom, that.createdDateTimeFrom) &&
                Objects.equals(this.createdDateTimeTo, that.createdDateTimeTo) &&
                Objects.equals(this.page, that.page) &&
                Objects.equals(this.size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, createdDateTimeFrom, createdDateTimeTo, page, size);
    }

    @Override
    public String toString() {
        return "GetAllCustomersQuery[" +
                "firstname=" + firstname + ", " +
                "lastname=" + lastname + ", " +
                "createdDateFrom=" + createdDateTimeFrom + ", " +
                "createdDateTo=" + createdDateTimeTo + ", " +
                "page=" + page + ", " +
                "size=" + size + ']';
    }

}
