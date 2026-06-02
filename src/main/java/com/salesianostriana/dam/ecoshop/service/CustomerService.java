package com.salesianostriana.dam.ecoshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.repository.CustomerRepository;
import com.salesianostriana.dam.ecoshop.service.base.BaseServiceImp;


@Service
public class CustomerService extends BaseServiceImp <Customer, Long, CustomerRepository> {

	private final CustomerRepository repository;
	
	public CustomerService(CustomerRepository repository) {
		super(repository);
        this.repository = repository;
		
    }
	
	/*
	public CustomerService(CustomerRepository repository) {
		this.repository = null;//CHECKEAR
		super(repository);
	}


	
	
	//NO CRUDS, clase para servicios personalizados
	
	/*
	public List<Customer> getLista() {
		return Arrays.asList(
				new Customer(1L, "Pepe", "pepe@gmail.com", "954 331 488", LocalDateTime.now(), 10000, true, null),//falta por poner el last value
				new Customer(2L, "Pepita", "pepita@gmail.com", "954 625 360", LocalDateTime.now(), 5, false, null)
				);		
	}
	
	
	public void addCustomer (Customer c) {
		repository.save(new Customer(1L, "Pepe", "pepe@gmail.com", "954 331 488", LocalDateTime.now(), 10000, true, null));
	}
	*/
	
	public Optional<Customer> findByUsername(String username) {
        return repository.findByUserUsername(username);
    }
	
	//para no pillar el logueado
	public List<Customer> findAllExceptUsername(String username) {
	    return repository.findAllExceptUsername(username);
	}
}
