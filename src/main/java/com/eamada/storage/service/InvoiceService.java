package com.eamada.storage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eamada.storage.model.Invoice;
import com.eamada.storage.repository.InvoiceRepository;

@Service
public class InvoiceService {
    private InvoiceRepository invoiceRepository;
    
    @Autowired
    public void setInvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
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
