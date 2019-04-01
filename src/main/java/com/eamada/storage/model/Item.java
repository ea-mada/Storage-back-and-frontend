package com.eamada.storage.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Item {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private int quantity;
	private int heightCm;
	private int widthCm;
	private long priceEuroCents;
	private long totalEuroCents;
	
	@ManyToOne
	@JoinColumn(name = "InvoiceId")
	@JsonIgnore
	private Customer customer;
	
	public Item() { }
	
	public Item(String name, int quantity, int heightCm, int widthCm, long priceEuroCents) {
		this.name = name;
		this.quantity = quantity;
		this.heightCm = heightCm;
		this.widthCm = widthCm;
		this.priceEuroCents = priceEuroCents;
		this.totalEuroCents = priceEuroCents * quantity;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public String getName() {
		return name;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public int getHeightCm() {
		return heightCm;
	}
	
	public int getWidthCm() {
		return widthCm;
	}
	
	public long getPriceEuroCents() {
		return priceEuroCents;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setHeightCm(int heightCm) {
		this.heightCm = heightCm;
	}
	
	public void setWidthCm(int widthCm) {
		this.widthCm = widthCm;
	}
	
	public void setPriceEuroCents(long priceEuroCents) {
		this.priceEuroCents = priceEuroCents;
	}
	
	public long getTotalEuroCents() {
		return this.totalEuroCents;
	}
	
}
