package com.eamada.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eamada.storage.model.InvoiceItem;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
	
}
