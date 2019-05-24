package com.eamada.storage.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eamada.storage.CreateCustomerCommand;
import com.eamada.storage.CreateInvoiceCommand;
import com.eamada.storage.CreateItemCommand;
import com.eamada.storage.model.Category;
import com.eamada.storage.model.Customer;
import com.eamada.storage.model.Invoice;
import com.eamada.storage.model.InvoiceItem;
import com.eamada.storage.model.Item;
import com.eamada.storage.model.UnitOfMeasurement;
import com.eamada.storage.service.CustomerService;
import com.eamada.storage.service.InvoiceItemService;
import com.eamada.storage.service.InvoiceService;
import com.eamada.storage.service.ItemService;

@RestController
@RequestMapping(value="/api/storage")
@CrossOrigin
public class StorageController {
	@Autowired
	private CustomerService service;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private InvoiceItemService invoiceItemService;
	
	@RequestMapping(path = "/deleteAllData", method = RequestMethod.DELETE)
	public void deleteAllData() {
		this.service.deleteAllData();
	}
	
	//From there, path = /customers
	
	@RequestMapping(path = "/customers/getCustomers", method = RequestMethod.GET)
	public Collection<Customer> getCustomers() {
		return this.service.getCustomers();
	}
	
	@RequestMapping(path = "/customers/getCustomer/{customerid}", method = RequestMethod.GET)
	public Customer getCustomer(@PathVariable @Valid Long customerid) {
		return this.service.getCustomer(customerid);
	}
	
	@RequestMapping(path = "/customers/addCustomer", method = RequestMethod.POST)
	public ResponseEntity<Customer> addCustomer(@RequestBody @Valid CreateCustomerCommand
			createCustomerCommand) {
		return this.service.addCustomer(createCustomerCommand);
	}
	
	@RequestMapping(path = "/customers/setCustomer/{customerid}", method = RequestMethod.PUT)
	public ResponseEntity<Customer> modifyCustomer(@PathVariable final Long customerid,
			@RequestBody @Valid CreateCustomerCommand createCustomerCommand) {
		return this.service.modifyCustomer(customerid, createCustomerCommand);
	}
	
	@RequestMapping(path = "/customers/deleteCustomer/{customerid}", method = RequestMethod.DELETE)
	public String deleteCustomer(@PathVariable final Long customerid) {
		return this.service.deleteCustomer(customerid);
	}

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
	
	//From there, path = /invoices
	
	@RequestMapping(path = "/invoices/getInvoiceById/{invoiceId}", method = RequestMethod.GET)
	public Invoice getInvoiceById(@PathVariable Long invoiceId) {
		return this.invoiceService.getInvoiceById(invoiceId);
	}
	
	@RequestMapping(path = "/invoices/addInvoice/{customerId}", method = RequestMethod.POST)
	public Invoice addInvoice(@PathVariable Long customerId, @RequestBody @Valid
			CreateInvoiceCommand createInvoiceCommand) {
		return this.invoiceService.addInvoice(customerId, createInvoiceCommand);
	}
	
	@RequestMapping(path = "invoices/setInvoice/{invoiceId}", method = RequestMethod.PUT)
	public Invoice setInvoice(@PathVariable Long invoiceId, @RequestBody CreateInvoiceCommand
			createInvoiceCommand) {
		return this.invoiceService.modifyInvoice(invoiceId, createInvoiceCommand);
	}
	
	//From there, path = /items
	
	@GetMapping("/items/categories")
	public ResponseEntity<Category[]> getCategories() {
		return this.itemService.getCategories();
	}
	
	@GetMapping("/items/unitsOfMeasurement")
	public ResponseEntity<UnitOfMeasurement[]> getUnitsOfMeasurement() {
		return this.itemService.getUnitsOfMeasurement();
	}
	
	@RequestMapping(path = "/items/getItems", method = RequestMethod.GET)
	public ResponseEntity<Collection<Item>> getItems() {
		return this.itemService.getItems();
	}
	
	@RequestMapping(path = "/items/getItem/{itemId}", method = RequestMethod.GET)
	public ResponseEntity<Item> getItem(@PathVariable Long itemId) {
		return this.itemService.getItem(itemId);
	}
	
	@RequestMapping(path = "/items/addItem", method = RequestMethod.POST)
	public ResponseEntity<Item> addItem(@RequestBody @Valid CreateItemCommand createItemCommand) {
		return this.itemService.addItem(createItemCommand);
	}
	
	@PutMapping(path = "/items/{itemId}")
	public ResponseEntity<Item> editItem(@RequestBody @Valid CreateItemCommand createItemCommand,
			@PathVariable Long itemId) {
		return this.itemService.editItem(createItemCommand, itemId);
	}
	
	
	@PostMapping("/invoicesItems/{invoiceId}/{itemId}/{quantity}") ResponseEntity<InvoiceItem> addInvoiceItem(@PathVariable Long invoiceId,
				@PathVariable Long itemId, @PathVariable int quantity) {
		return new ResponseEntity<InvoiceItem>(this.invoiceItemService.addInvoiceItem(invoiceId, itemId, quantity),
				HttpStatus.OK);
	}
	
	
//	@RequestMapping(path = "/items/getItem/{itemid}", method = RequestMethod.PUT)
//	public Item modifyItem(@PathVariable final Long itemid,
//			@RequestBody @Valid CreateItemCommand createItemCommand) {
//		return this.service.modifyItem(itemid, createItemCommand);
//	}
	
//	@RequestMapping(path = "/delete/item/{itemid}", method = RequestMethod.DELETE)
//	public String deleteItem(@PathVariable final long itemid) {
//		return this.service.deleteItem(itemid);
//	}
}
