package com.salesianostriana.dam.ecoshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.model.Product;

@Repository
public interface CustomerRepository 
	extends JpaRepository<Customer, Long>{

	/*
	//consultas
		public List<Customer> findById(String Long);
		
		public List<Customer> findAll (String Long);
		*/
	
}
