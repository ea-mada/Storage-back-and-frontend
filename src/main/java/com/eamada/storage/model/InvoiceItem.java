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
	@JoinTable(name = "itemId")
	Item item;
	
	int quantity;
}
