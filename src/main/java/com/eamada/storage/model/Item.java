package com.eamada.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

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

//    @ManyToMany
//    @JoinTable(
//            name = "invoice_item",
//            joinColumns = @JoinColumn(name = "item_id"),
//            inverseJoinColumns = @JoinColumn(name = "invoice_id")
//    )
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonIgnore
    private Invoice invoice;

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