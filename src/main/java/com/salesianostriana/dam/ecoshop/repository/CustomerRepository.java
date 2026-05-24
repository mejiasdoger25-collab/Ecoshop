package com.salesianostriana.dam.ecoshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.ecoshop.model.Customer;

@Repository
public interface CustomerRepository 
	extends JpaRepository<Customer, Long>{

	Optional<Customer> findByUserUsername(String username);
	
	/*
	//consultas
		public List<Customer> findById(String Long);
		
		public List<Customer> findAll (String Long);
		*/
	
}
