package com.eamada.storage.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eamada.storage.CreateCustomerCommand;
import com.eamada.storage.CreateItemCommand;
import com.eamada.storage.model.Customer;
import com.eamada.storage.model.Invoice;
import com.eamada.storage.model.Item;
import com.eamada.storage.repository.InvoiceRepository;
import com.eamada.storage.service.CustomerService;
import com.eamada.storage.service.InvoiceService;

@RestController
@RequestMapping(value="/api/storage")
@CrossOrigin
public class StorageController {
	@Autowired
	private CustomerService service;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@RequestMapping(path = "/customers/getCustomers", method = RequestMethod.GET)
	public Collection<Customer> getCustomers() {
		return this.service.getCustomers();
	}
	
	@RequestMapping(path = "/customers/getCustomer/{customerid}", method = RequestMethod.GET)
	public Customer getCustomer(@PathVariable @Valid Long customerid) {
		return this.service.getCustomer(customerid);
	}
	
//	@RequestMapping(path = "/{customerid}/items", method = RequestMethod.GET)
//	public Collection<Item> getItems(@PathVariable final Long customerid) {
//		return this.service.getItems(customerid);
//	}
	
	@RequestMapping(path = "/customers/addCustomer", method = RequestMethod.POST)
	public ResponseEntity<Customer> addCustomer(@RequestBody @Valid CreateCustomerCommand
			createCustomerCommand) {
		return this.service.addCustomer(createCustomerCommand);
	}
	
//	@RequestMapping(value = "{customerid}/item", method = RequestMethod.POST)
//	public Item addItem(@PathVariable @Valid final Long customerid,
//			@RequestBody @Valid CreateItemCommand createItemCommand) {
//		return this.service.addItem(customerid, createItemCommand);
//	}
	
	@RequestMapping(path = "/customers/setCustomer/{customerid}", method = RequestMethod.PUT)
	public ResponseEntity<Customer> modifyCustomer(@PathVariable final Long customerid,
			@RequestBody @Valid CreateCustomerCommand createCustomerCommand) {
		return this.service.modifyCustomer(customerid, createCustomerCommand);
	}
	
//	@RequestMapping(path = "{itemid}/item", method = RequestMethod.PUT)
//	public Item modifyItem(@PathVariable final Long itemid,
//			@RequestBody @Valid CreateItemCommand createItemCommand) {
//		return this.service.modifyItem(itemid, createItemCommand);
//	}
	
	@RequestMapping(path = "/customers/deleteCustomer/{customerid}", method = RequestMethod.DELETE)
	public String deleteCustomer(@PathVariable final Long customerid) {
		return this.service.deleteCustomer(customerid);
	}
	
//	@RequestMapping(path = "/delete/item/{itemid}", method = RequestMethod.DELETE)
//	public String deleteItem(@PathVariable final long itemid) {
//		return this.service.deleteItem(itemid);
//	}

	@RequestMapping(path = "/customers/prefillName/{customerNameFragment}",
			method = RequestMethod.GET)
	public List<Customer> getCustomersThatMatchesString(@PathVariable 
			final String customerNameFragment) {
		return this.service.getCustomersWithNamesMatching(customerNameFragment);
	}
	
	@RequestMapping(path = "/customers/prefillVatcode/{customerVatcodeFragment}",
			method = RequestMethod.GET)
	public List<Customer> getCustomersMatchingVatCode(@PathVariable 
			final String customerVatcodeFragment) {
		return this.service.getCustomersMatchingVatcode(customerVatcodeFragment);
	}
	
	@RequestMapping(path = "/deleteAllData", method = RequestMethod.DELETE)
	public void deleteAllData() {
		this.service.deleteAllData();
	}
	
	@RequestMapping(path = "invoices/findInvoiceById/{invoiceId}", method = RequestMethod.GET)
	public Invoice findInvoiceById(@PathVariable Long invoiceId) {
		return this.invoiceService.getInvoiceById(invoiceId);
	}
	
	@RequestMapping(path = "/invoices/addInvoice/{customerId}", method = RequestMethod.POST)
	public void addInvoice(@PathVariable Long customerId, @RequestBody Invoice invoice) {
		this.invoiceService.createInvoice(invoice);
	}
	
}
