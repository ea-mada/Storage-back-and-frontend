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
	
	public String deleteCustomer(Long customerid) {
		if (customerRepository.findById(customerid).orElse(null) != null) {
			this.customerRepository.deleteById(customerid);
			return "Customer with Id " + customerid + " was removed.";
		}
		return "! There is no customer with id: '" + customerid
				+ "'. No customer was deleted.";
	}
	

	
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