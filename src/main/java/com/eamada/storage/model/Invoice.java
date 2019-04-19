package com.eamada.storage.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Invoice {

    @Id
    @GeneratedValue
    private Long id;
    private long number;
    @CreationTimestamp
    private LocalDate creationDate;
    private String prescribedCompany;
    private String receiver;

//    @ManyToMany
//    @JoinTable(
//            name = "invoice_item",
//            joinColumns = @JoinColumn(name = "invoice_id"),
//            inverseJoinColumns = @JoinColumn(name = "item_id")
//    )
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<Item> items;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer", insertable = false, updatable = false)
    private Customer customer;
    
    public void setCustomer(Customer customer) {
    	this.customer = customer;
    }
    
    public Customer getCustomer() {
    	return customer;
    }

    public Invoice(){}

    public Invoice(long number, String prescribedCompany, String receiver) {
        this.number = number;
        this.prescribedCompany = prescribedCompany;
        this.receiver = receiver;
    }

}
