package com.eamada.storage.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    @OneToMany(mappedBy = "item")
    private Set<InvoiceItem> invoicesItems;

	public Item(String name, UnitOfMeasurement unitOfMeasurement, Category category, double price) {
		this.name = name;
		this.price = price;
		this.unitOfMeasurement = unitOfMeasurement;
		this.category = category;
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