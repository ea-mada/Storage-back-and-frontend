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
    private Long invoiceId;
    @CreationTimestamp
    private LocalDate dateOfPurchase;
    private String receivingCompany;
//    @Column(columnDefinition="constant")
    private String distributor;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceItem> invoiceItems;
    
    @ManyToOne
    @JoinColumn(name = "customerId")
    @JsonIgnore
    private Customer customer;
    
    public void setCustomer(Customer customer) {
    	this.customer = customer;
//    	this.customerId = customer.getCustomerid();
    }
    
    public Customer getCustomer() {
    	return customer;
    }

    public Invoice(){
    	
    }

    public Invoice(String receivingCompany, String distributor) {
        this.receivingCompany = receivingCompany;
        this.distributor = distributor;
    }

    
}
