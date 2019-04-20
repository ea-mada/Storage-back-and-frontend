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
    private String distributor;

//    @ManyToMany
//    @JoinTable(
//            name = "invoice_item",
//            joinColumns = @JoinColumn(name = "invoice_id"),
//            inverseJoinColumns = @JoinColumn(name = "item_id")
//    )
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<Item> items;
    
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

    public Invoice(){}

    public Invoice(String receivingCompany, String distributor) {
        this.receivingCompany = receivingCompany;
        this.distributor = distributor;
    }

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getReceivingCompany() {
		return receivingCompany;
	}

	public void setReceivingCompany(String receivingCompany) {
		this.receivingCompany = receivingCompany;
	}

	public LocalDate getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(LocalDate dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

    
}
