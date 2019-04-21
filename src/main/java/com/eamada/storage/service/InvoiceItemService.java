package com.eamada.storage.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.eamada.storage.repository.InvoiceRepository;
import com.eamada.storage.repository.ItemRepository;

public class InvoiceItemService {
	private InvoiceRepository invoiceRepository;
	private ItemRepository itemRepository;
	
	@Autowired
	public InvoiceItemService(InvoiceRepository invoiceRepository, ItemRepository itemRepository) {
		this.invoiceRepository = invoiceRepository;
		this.itemRepository = itemRepository;
	}
}
