package com.eamada.storage.model;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class InvoiceItem implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5055572764661712803L;

	@Id
	@ManyToOne
	@JoinColumn
	private Item item;
	
	@Id
	@ManyToOne
	@JoinColumn
	private Invoice invoice;
	
	private int quantity;
	
	public InvoiceItem(Invoice invoice, Item item, int quantity) {
		this.invoice = invoice;
		this.quantity = quantity;
		this.item = item;
	}
}
