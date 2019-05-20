package com.eamada.storage.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;

@Entity
@Data
public class InvoiceItem {
	@EmbeddedId
	InvoiceItemPK invoiceItemPK;
	
	@ManyToOne
	@MapsId("invoiceId")
	@JoinColumn(name = "invoiceId")
	Invoice invoice;
	
	@ManyToOne
	@MapsId("itemId")
	@JoinColumn(name = "itemId")
	Item item;
	
	int quantity;
	
	public InvoiceItem() { }

	public InvoiceItem(Invoice invoice, Item item, int quantity) {
		this.invoice = invoice;
		this.item = item;
		this.quantity = quantity;
	}
	
	
}
