package com.eamada.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eamada.storage.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
