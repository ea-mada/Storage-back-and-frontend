package com.eamada.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eamada.storage.model.InvoiceItem;
import com.eamada.storage.model.InvoiceItemPK;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, InvoiceItemPK> {

}
