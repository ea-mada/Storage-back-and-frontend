package com.eamada.storage.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	@DeleteMapping(path = "/deleteAllData")
	public void deleteAllData() {
		this.service.deleteAllData();
	}
	
	//From there, path = /customers
	
	@GetMapping(path = "/customers/getCustomers")
	public Collection<Customer> getCustomers() {
		return this.service.getCustomers();
	}
	
	@GetMapping(path = "/customers/getCustomer/{customerid}")
	public Customer getCustomer(@PathVariable @Valid Long customerid) {
		return this.service.getCustomer(customerid);
	}
	
	@PostMapping(path = "/customers/addCustomer")
	public ResponseEntity<Customer> addCustomer(@RequestBody @Valid CreateCustomerCommand
			createCustomerCommand) {
		return this.service.addCustomer(createCustomerCommand);
	}
	
	@PutMapping(path = "/customers/setCustomer/{customerid}")
	public ResponseEntity<Customer> modifyCustomer(@PathVariable final Long customerid,
			@RequestBody @Valid CreateCustomerCommand createCustomerCommand) {
		return this.service.modifyCustomer(customerid, createCustomerCommand);
	}
	
	@DeleteMapping(path = "/customers/deleteCustomer/{customerid}")
	public String deleteCustomer(@PathVariable final Long customerid) {
		return this.service.deleteCustomer(customerid);
	}

	@GetMapping(path = "/customers/prefillName/{customerNameFragment}")
	public List<Customer> getCustomersThatMatchesString(@PathVariable 
			final String customerNameFragment) {
		return this.service.getCustomersWithNamesMatching(customerNameFragment);
	}
	
	@GetMapping(path = "/customers/prefillVatcode/{customerVatcodeFragment}")
	public List<Customer> getCustomersMatchingVatCode(@PathVariable 
			final String customerVatcodeFragment) {
		return this.service.getCustomersMatchingVatcode(customerVatcodeFragment);
	}
	
	//From there, path = /invoices
	
	@GetMapping(path = "/invoices/getInvoiceById/{invoiceId}")
	public Invoice getInvoiceById(@PathVariable Long invoiceId) {
		return this.invoiceService.getInvoiceById(invoiceId);
	}
	
	@PostMapping(path = "/invoices/addInvoice/{customerId}")
	public Invoice addInvoice(@PathVariable Long customerId, @RequestBody @Valid
			CreateInvoiceCommand createInvoiceCommand) {
		return this.invoiceService.addInvoice(customerId, createInvoiceCommand);
	}
	
	@PutMapping(path = "invoices/setInvoice/{invoiceId}")
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
	
	@GetMapping(path = "/items/getItems")
	public ResponseEntity<Collection<Item>> getItems() {
		return this.itemService.getItems();
	}
	
	@GetMapping(path = "/items/getItem/{itemId}")
	public ResponseEntity<Item> getItem(@PathVariable Long itemId) {
		return this.itemService.getItem(itemId);
	}
	
	@PostMapping(path = "/items/addItem")
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
	
	
//	@RequestMapping(path = "/items/getItem/{itemid}")
//	public Item modifyItem(@PathVariable final Long itemid,
//			@RequestBody @Valid CreateItemCommand createItemCommand) {
//		return this.service.modifyItem(itemid, createItemCommand);
//	}
	
//	@RequestMapping(path = "/delete/item/{itemid}")
//	public String deleteItem(@PathVariable final long itemid) {
//		return this.service.deleteItem(itemid);
//	}
}