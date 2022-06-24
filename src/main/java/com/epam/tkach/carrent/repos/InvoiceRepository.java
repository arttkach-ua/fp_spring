package com.epam.tkach.carrent.repos;

import com.epam.tkach.carrent.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
