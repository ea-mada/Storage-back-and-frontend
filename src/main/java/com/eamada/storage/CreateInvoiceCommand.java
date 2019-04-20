package com.eamada.storage;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class CreateInvoiceCommand {
	@NotNull
	private LocalDate dateOfPurchase;
	@NotNull
    private String receivingCompany;
	@NotNull
    private String distributor;
	
	public CreateInvoiceCommand() { }
	
	public CreateInvoiceCommand(LocalDate dateOfPurchase, String receivingCompany,
			String distributor) {
		this.dateOfPurchase = dateOfPurchase;
		this.receivingCompany = receivingCompany;
		this.distributor = distributor;
	}

	public LocalDate getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(LocalDate dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public String getReceivingCompany() {
		return receivingCompany;
	}

	public void setReceivingCompany(String receivingCompany) {
		this.receivingCompany = receivingCompany;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
	
	
}
