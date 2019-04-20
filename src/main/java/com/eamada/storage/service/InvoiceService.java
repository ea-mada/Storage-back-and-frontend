package com.eamada.storage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eamada.storage.CreateInvoiceCommand;
import com.eamada.storage.model.Invoice;
import com.eamada.storage.repository.CustomerRepository;
import com.eamada.storage.repository.InvoiceRepository;

@Service
public class InvoiceService {
	private InvoiceRepository invoiceRepository;
    private CustomerRepository customerRepository;
    
    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, CustomerRepository customerRepository) {
    	super();
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
    }

    public Invoice addInvoice(Long customerId, CreateInvoiceCommand createInvoiceCommand) {
    	Invoice invoice = new Invoice(createInvoiceCommand.getReceivingCompany(),
    			createInvoiceCommand.getDistributor());
    	invoice.setCustomer(this.customerRepository.getOne(customerId));
    	this.createInvoice(invoice);
    	//this.customerRepository.getOne(customerId).addInvoice(invoice);
		//this.invoiceRepository.getOne(createItemCommand.getInvoiceId()).addItem(item);
		
		return invoice;
	}
    

public Invoice modifyInvoice(Long invoiceId, CreateInvoiceCommand createInvoiceCommand) {
	if (invoiceId == null || this.findInvoiceById(invoiceId) == null) { 
		return null;
	}
	
	Invoice oldInvoice = this.getInvoiceById(invoiceId);
	
	if (createInvoiceCommand.getDateOfPurchase() != null) {
		oldInvoice.setDateOfPurchase(createInvoiceCommand.getDateOfPurchase());
	}
	
	if (createInvoiceCommand.getDistributor() != null) {
		oldInvoice.setDistributor(createInvoiceCommand.getDistributor());
	}
	
	if (createInvoiceCommand.getReceivingCompany() != null) {
		oldInvoice.setReceivingCompany(createInvoiceCommand.getReceivingCompany());
	}
	
	invoiceRepository.save(oldInvoice);
	
	return oldInvoice;
}

    private Invoice findInvoiceById(long id){
        for (Invoice invoice : getInvoicesList()) {
            if (invoice.getInvoiceId() == id) {
                return invoice;
            }
        }
        return null;
    }


    public void deleteInvoiceById(Long id) {
        invoiceRepository.deleteById(id);
    }

    public void createInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public List<Invoice> getInvoicesList() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.getOne(id);
    }

}
