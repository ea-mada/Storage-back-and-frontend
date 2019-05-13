package com.eamada.storage.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eamada.storage.model.InvoiceItem;
import com.eamada.storage.repository.InvoiceItemRepository;
import com.eamada.storage.repository.InvoiceRepository;
import com.eamada.storage.repository.ItemRepository;
@Service
public class InvoiceItemService {
	private InvoiceRepository invoiceRepository;
	private ItemRepository itemRepository;
	private InvoiceItemRepository invoiceItemRepository;
	
	@Autowired
	public InvoiceItemService(InvoiceRepository invoiceRepository, ItemRepository itemRepository,
			InvoiceItemRepository invoiceItemRepository) {
		this.invoiceRepository = invoiceRepository;
		this.itemRepository = itemRepository;
		this.invoiceItemRepository = invoiceItemRepository;
	}
	
//	public Collection<InvoiceItem> getAll(InvoiceRepository invoiceRepository, ItemRepository itemRepository) {
//		
//	}
	
	public Collection<InvoiceItem> getAll() {
		return this.invoiceItemRepository.findAll();
	}
	
	

	

	public void addInvoiceItem(Long invoiceId, Long itemId, int quantity) {
		this.invoiceItemRepository.save(new InvoiceItem(invoiceRepository.findById(invoiceId).orElse(null), 
				itemRepository.findById(itemId).orElse(null), quantity));
	}
}
