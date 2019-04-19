package com.eamada.storage.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long customerid;
	
	@Column(name="vatcode")
	private String vatCode;
	private String name;
	private String address;
	@Column(name="phonenumber")
	private String phoneNumber;
	private String iban;
	private String notes;
	
	@OneToMany(mappedBy = "customer", cascade=CascadeType.ALL)
	private Set<Invoice> invoices = new HashSet<>();
	
	public Collection<Invoice> getInvoices() {
		return this.invoices;
	}
	
	public void addInvoice(Invoice invoice) {
		this.invoices.add(invoice);
		invoice.setCustomer(this); 
	}
	
	public Customer() { }
	
	public Customer(String vatCode, String name, String address,
			String phoneNumber, String iban, String notes) {
		this.vatCode = vatCode;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.iban = iban;
		this.notes = notes;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public String getVatCode() {
		return vatCode;
	}

	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}
	
}
