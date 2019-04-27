package com.eamada.storage.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class Item {

    @Id
    @GeneratedValue
    private Long itemId;
    private String name;
    private double price;
    @Enumerated(EnumType.STRING)
    private UnitOfMeasurement unitOfMeasurement;
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "invoice_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "invoice_id")
    )
    private List<Invoice> invoices;

    public Item(){}

    public Item(String name, UnitOfMeasurement unitOfMeasurement, Category category, double price) {
    	this.name = name;
    	this.unitOfMeasurement = unitOfMeasurement;
    	this.category = category;
    	this.price = price;
    }

	public Long getItemId() {
		return itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public UnitOfMeasurement getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
    
//    public Item(String name, double price, UnitOfMeasurement unitOfMeasurement, int quantity) {
//        this.name = name;
//        this.price = price;
//        this.unitOfMeasurement = unitOfMeasurement;
//        this.quantity = quantity;
//    }
//
//    @Transient
//    public double getFinalPrice(){
//        return quantity * price;
//    }
    
    
    
}