package com.eamada.storage.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class InvoiceItemPK implements Serializable {
	private Long invoiceId;
	private Long itemId;
	
	public InvoiceItemPK(Long invoiceId, Long itemId) {
		super();
		this.invoiceId = invoiceId;
		this.itemId = itemId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public Long getItemId() {
		return itemId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
	}
	
}
