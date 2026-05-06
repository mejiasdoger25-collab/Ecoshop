package com.salesianostriana.dam.ecoshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.ecoshop.model.Customer;

@Repository
public interface CustomerRepository 
	extends JpaRepository<Customer, Long>{

}
