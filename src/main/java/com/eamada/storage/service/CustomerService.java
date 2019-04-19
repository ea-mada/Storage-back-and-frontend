package com.eamada.storage.service;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eamada.storage.CreateCustomerCommand;
import com.eamada.storage.model.Customer;
import com.eamada.storage.repository.CustomerRepository;
import com.eamada.storage.repository.InvoiceRepository;

@Service
public class CustomerService {
	
	private CustomerRepository customerRepository;
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	public CustomerService(CustomerRepository customerRepository,
			InvoiceRepository invoiceRepository) {
		super();
		this.customerRepository = customerRepository;
		this.invoiceRepository = invoiceRepository;
	}

	private Logger log = Logger.getLogger(CustomerService.class.getName());
	
	public Collection<Customer> getCustomers() {
		return this.customerRepository.findAll();
	}
	
	public Customer getCustomer(Long customerid) {
		return this.customerRepository.findById(customerid).orElse(null);
	}
	
	public ResponseEntity<Customer> addCustomer(CreateCustomerCommand createCustomerCommand) {
		if (!isUniqueVatCode(createCustomerCommand.getVatCode())) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		Customer customer = new Customer(createCustomerCommand.getVatCode(), 
				createCustomerCommand.getName(), createCustomerCommand.getAddress(),
					createCustomerCommand.getPhoneNumber(), createCustomerCommand
						.getIban(), createCustomerCommand.getNotes());
		
		this.customerRepository.save(customer);
		
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
//	public Item addItem(Long id, CreateItemCommand createItemCommand) {
//		Item item = new Item(createItemCommand.getName(), createItemCommand.getQuantity(),
//				createItemCommand.getHeightCm(), createItemCommand.getWidthCm(),
//					createItemCommand.getPriceEuroCents());
//		item.setCustomer(customerRepository.getOne(id));
//		this.itemRepository.save(item);
//		//this.invoiceRepository.getOne(createItemCommand.getInvoiceId()).addItem(item);
//		
//		return item;
//	}
	
	public String deleteCustomer(Long customerid) {
		if (customerRepository.findById(customerid).orElse(null) != null) {
			this.customerRepository.deleteById(customerid);
			return "Customer with Id " + customerid + " was removed.";
		}
		return "! There is no customer with id: '" + customerid
				+ "'. No customer was deleted.";
	}
	
//	public String deleteItem(Long id) {
//		if (itemRepository.findById(id).orElse(null) != null) {
//			itemRepository.deleteById(id);
//			return "Item with Id " + id + " was removed.";
//		}
//		return "! There is no item with id: '" + id + "'. No item was deleted.";
//	}
	
//	public Item modifyItem(Long itemid, CreateItemCommand createItemCommand) {
//		if (itemid == null || itemRepository.getOne(itemid) == null) {
//			return null;
//		}
//		Item item = this.itemRepository.getOne(itemid);
//		item.setHeightCm(createItemCommand.getHeightCm());
//		item.setCustomer(this.itemRepository.getOne(itemid).getCustomer());
//		item.setName(createItemCommand.getName());
//		item.setPriceEuroCents(createItemCommand.getPriceEuroCents());
//		item.setQuantity(createItemCommand.getQuantity());
//		item.setWidthCm(createItemCommand.getWidthCm());
//		itemRepository.save(item);
//		
//		return item;
//	}
	
	private boolean isUniqueVatCode(String newVatcode) {
		return !this.customerRepository.findAll().stream()
				.anyMatch(c -> c.getVatCode().equals(newVatcode));
	}
	
	private boolean isVatcodeUniqueOrUnchanged(String vatCode, Long customerid) {
		return !this.customerRepository.findAll().stream()
				.anyMatch(c -> c.getVatCode().equals(vatCode) && c.getCustomerid().longValue()
						!= customerid.longValue());
	}
	
	public ResponseEntity<Customer> modifyCustomer(Long customerid, CreateCustomerCommand
				createCustomerCommand) {
		if (!isVatcodeUniqueOrUnchanged(createCustomerCommand.getVatCode(), customerid)) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		if (customerid == null || customerRepository.findById(customerid) == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		Customer customer = this.customerRepository.getOne(customerid);
		
		customer.setVatCode(createCustomerCommand.getVatCode());
		customer.setName(createCustomerCommand.getName());
		customer.setAddress(createCustomerCommand.getAddress());
		customer.setPhoneNumber(createCustomerCommand.getPhoneNumber());
		customer.setIban(createCustomerCommand.getIban());
		customer.setNotes(createCustomerCommand.getNotes());
		
		this.customerRepository.save(customer);
		
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
//	public Collection<Item> getItems(Long id) {
//		return this.customerRepository.getOne(id).getInvoices();
//	}
	
	public List<Customer> getCustomersWithNamesMatching
			(String customerNameFragment) {
		return this.customerRepository.findAll().stream()
		.limit(500000)
		.filter(c -> c.getName().toLowerCase()
		.contains(customerNameFragment.toLowerCase()))
		.limit(21)
		.sorted((c1, c2) -> c1.getName().toLowerCase().indexOf(customerNameFragment.toLowerCase())
				- c2.getName().toLowerCase().indexOf(customerNameFragment.toLowerCase()))
		.collect(Collectors.toList());
	}
	
	public List<Customer> getCustomersMatchingVatcode
	(String customerVatcodeFragment) {
		
		return this.customerRepository.findAll().stream()
		.limit(500000)
		.filter(c -> c.getVatCode().toLowerCase()
		.contains(customerVatcodeFragment.toLowerCase()))
		.limit(21)
		.sorted((c1, c2) -> c1.getVatCode().toLowerCase().indexOf(customerVatcodeFragment.toLowerCase())
				- c2.getVatCode().toLowerCase().indexOf(customerVatcodeFragment.toLowerCase()))
		.collect(Collectors.toList());
}
	
	public void deleteAllData() {
		this.customerRepository.deleteAll();
	}
}