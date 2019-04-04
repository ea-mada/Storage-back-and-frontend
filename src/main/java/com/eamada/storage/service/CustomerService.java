package com.eamada.storage.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eamada.storage.CreateCustomerCommand;
import com.eamada.storage.CreateItemCommand;
import com.eamada.storage.CustomerNameAndIdKeeper;
import com.eamada.storage.CustomerVatcodeAndIdKepper;
import com.eamada.storage.model.Customer;
import com.eamada.storage.model.Item;
import com.eamada.storage.repository.CustomerRepository;
import com.eamada.storage.repository.ItemRepository;

@Service
public class CustomerService {
	
	private CustomerRepository customerRepository;
	private ItemRepository itemRepository;
	
//	@Transient
//	private Set<CustomerNameAndIdKeeper> customerNamesAndIds = new 
//		TreeSet<>((c1, c2) -> c1.getName().toLowerCase()
//		.compareTo(c2.getName().toLowerCase()));
	
	@Transient
	private List<CustomerNameAndIdKeeper> customerNamesAndIds = new 
		ArrayList<>();
	
//	@Transient
//	private Set<CustomerVatcodeAndIdKepper> customerVatcodesAndIds = new 
//		TreeSet<>((c1, c2) -> c1.getVatCode().toLowerCase()
//		.compareTo(c2.getVatCode().toLowerCase()));
	
	@Transient
	private List<CustomerVatcodeAndIdKepper> customerVatcodesAndIds = new 
		ArrayList<>();
	
	@Autowired
	public CustomerService(CustomerRepository customerRepository,
			ItemRepository itemRepository) {
		super();
		this.customerRepository = customerRepository;
		this.itemRepository = itemRepository;
		this.customerRepository.findAll()
		.forEach(c -> {customerNamesAndIds
				.add(new CustomerNameAndIdKeeper(c.getName(), c.getCustomerid()));
					customerVatcodesAndIds.add
						(new CustomerVatcodeAndIdKepper(c.getVatCode(), c.getCustomerid()));});
	}

	private Logger log = Logger.getLogger(CustomerService.class.getName());
	
	public Collection<Customer> getCustomers() {
		return this.customerRepository.findAll();
	}
	
	public Customer getCustomer(Long customerid) {
		return this.customerRepository.findById(customerid).orElse(null);
	}
	
	public ResponseEntity<Customer> addCustomer(CreateCustomerCommand createCustomerCommand) {
		Customer customer = new Customer(createCustomerCommand.getVatCode(), 
				createCustomerCommand.getName(), createCustomerCommand.getAddress(),
					createCustomerCommand.getPhoneNumber(), createCustomerCommand
						.getIban(), createCustomerCommand.getNotes());
		
		this.customerRepository.save(customer);
		this.customerNamesAndIds.add(new CustomerNameAndIdKeeper
				(customer.getName(), customer.getCustomerid()));
		this.customerVatcodesAndIds.add(new CustomerVatcodeAndIdKepper
				(customer.getVatCode(), customer.getCustomerid()));
		
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	public Item addItem(Long id, CreateItemCommand createItemCommand) {
		Item item = new Item(createItemCommand.getName(), createItemCommand.getQuantity(),
				createItemCommand.getHeightCm(), createItemCommand.getWidthCm(),
					createItemCommand.getPriceEuroCents());
		item.setCustomer(customerRepository.getOne(id));
		this.itemRepository.save(item);
		//this.invoiceRepository.getOne(createItemCommand.getInvoiceId()).addItem(item);
		
		return item;
	}
	
	public String deleteCustomer(Long customerid) {
		if (customerRepository.findById(customerid).orElse(null) != null) {
			this.customerRepository.deleteById(customerid);
			this.customerNamesAndIds.removeIf(c -> c.getId() == customerid);
			this.customerVatcodesAndIds.removeIf(c -> c.getId() == customerid);
			return "Customer with Id " + customerid + " was removed.";
		}
		return "! There is no customer with id: '" + customerid
				+ "'. No customer was deleted.";
	}
	
	public String deleteItem(Long id) {
		if (itemRepository.findById(id).orElse(null) != null) {
			itemRepository.deleteById(id);
			return "Item with Id " + id + " was removed.";
		}
		return "! There is no item with id: '" + id + "'. No item was deleted.";
	}
	
	public Item modifyItem(Long itemid, CreateItemCommand createItemCommand) {
		if (itemid == null || itemRepository.getOne(itemid) == null) {
			return null;
		}
		Item item = this.itemRepository.getOne(itemid);
		item.setHeightCm(createItemCommand.getHeightCm());
		item.setCustomer(this.itemRepository.getOne(itemid).getCustomer());
		item.setName(createItemCommand.getName());
		item.setPriceEuroCents(createItemCommand.getPriceEuroCents());
		item.setQuantity(createItemCommand.getQuantity());
		item.setWidthCm(createItemCommand.getWidthCm());
		itemRepository.save(item);
		
		return item;
	}
	
	public Customer modifyCustomer(Long customerid, CreateCustomerCommand
				createCustomerCommand) {
		if (customerid == null || customerRepository.findById(customerid) == null) {
			return null;
		}
		Customer customer = this.customerRepository.getOne(customerid);
		
		customer.setVatCode(createCustomerCommand.getVatCode());
		customer.setName(createCustomerCommand.getName());
		customer.setAddress(createCustomerCommand.getAddress());
		customer.setPhoneNumber(createCustomerCommand.getPhoneNumber());
		customer.setIban(createCustomerCommand.getIban());
		customer.setNotes(createCustomerCommand.getNotes());
		
		this.customerRepository.save(customer);
		this.customerNamesAndIds.removeIf(c -> c.getId() == customer.getCustomerid());
		this.customerNamesAndIds.add
				(new CustomerNameAndIdKeeper(customer.getName(), customer.getCustomerid()));
		this.customerVatcodesAndIds.removeIf(c -> c.getId() == customer.getCustomerid());
		this.customerVatcodesAndIds.add
			(new CustomerVatcodeAndIdKepper(customer.getVatCode(), customer.getCustomerid()));
		
		return customer;
	}
	
	public Collection<Item> getItems(Long id) {
		return this.customerRepository.getOne(id).getItems();
	}
	
	public List<CustomerNameAndIdKeeper> getCustomersWithNamesMatching
			(String customerNameFragment) {
		return this.customerNamesAndIds.stream()
		.limit(500000)
		.filter(c -> c.getName().toLowerCase()
		.contains(customerNameFragment.toLowerCase()))
		.limit(21)
		.sorted((c1, c2) -> c1.getName().toLowerCase().indexOf(customerNameFragment)
				- c2.getName().toLowerCase().indexOf(customerNameFragment))
		.collect(Collectors.toList());
	}
	
	public List<CustomerVatcodeAndIdKepper> getCustomersMatchingVatcode
	(String customerVatcodeFragment) {
		
		return this.customerVatcodesAndIds.stream()
		.limit(500000)
		.filter(c -> c.getVatCode().toLowerCase()
		.contains(customerVatcodeFragment.toLowerCase()))
		.limit(21)
		.sorted((c1, c2) -> c1.getVatCode().toLowerCase().indexOf(customerVatcodeFragment)
				- c2.getVatCode().toLowerCase().indexOf(customerVatcodeFragment))
		.collect(Collectors.toList());
}
}