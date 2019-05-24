package com.eamada.storage.service;

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
	private InvoiceItemRepository InvoiceItemRepository;
	
	
	public InvoiceItemService(InvoiceRepository invoiceRepository, ItemRepository itemRepository,
			InvoiceItemRepository invoiceItemRepository) {
		this.invoiceRepository = invoiceRepository;
		this.itemRepository = itemRepository;
		InvoiceItemRepository = invoiceItemRepository;
	}

	public InvoiceItem addInvoiceItem(Long invoiceId, Long itemId, int quantity) {
		InvoiceItem newInvoiceItem = new InvoiceItem(this.invoiceRepository.findById(invoiceId).orElse(null),
				this.itemRepository.findById(itemId).orElse(null),
					quantity);
		
		this.InvoiceItemRepository.save(newInvoiceItem);
		return newInvoiceItem;
	}
}
