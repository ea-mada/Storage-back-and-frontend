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
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
public class Item {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double price;
    @Enumerated(EnumType.STRING)
    private UnitOfMeasurement unitOfMeasurement;
    private int quantity;

    @ManyToMany
    @JoinTable(
            name = "invoice_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "invoice_id")
    )
    private List<Invoice> invoices;

    public Item(){}

    public Item(String name, double price, UnitOfMeasurement unitOfMeasurement, int quantity) {
        this.name = name;
        this.price = price;
        this.unitOfMeasurement = unitOfMeasurement;
        this.quantity = quantity;
    }

    @Transient
    public double getFinalPrice(){
        return quantity * price;
    }
}