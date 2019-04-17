package com.eamada.storage;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class CreateCustomerCommand {
	private String vatCode;
	
	@NotNull
	@Length(min = 1, max = 255)
	private String name;
	@NotNull
	@Length(min = 1, max = 255)
	private String address;
	@NotNull
	@Length(min = 1, max = 255)
	private String phoneNumber;
	@NotNull
	@Length(min = 1, max = 34)
	private String iban;

	@Length(max = 4000)
	private String notes;
	
	public CreateCustomerCommand() { }

	public String getVatCode() {
		return vatCode;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getIban() {
		return iban;
	}

	public String getNotes() {
		return notes;
	}

}