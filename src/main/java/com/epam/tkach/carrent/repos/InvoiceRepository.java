package com.epam.tkach.carrent.repos;

import com.epam.tkach.carrent.entity.Invoice;
import com.epam.tkach.carrent.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    @Query("select i from Invoice i where " +
            "(:paidFilter is null or i.paid=:paidFilter) and" +
            "(:userFilter is null or i.client=:userFilter)")
    Page<Invoice> findInvoicesCustom(Pageable pageable,
                                 @Param("paidFilter") Boolean paidFilter,
                                 @Param("userFilter") User userFilter);
}
