package com.eamada.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eamada.storage.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
}
