package com.salesianostriana.dam.ecoshop.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.repository.CustomerRepository;
import com.salesianostriana.dam.ecoshop.service.base.BaseServiceImp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor
//@AllArgsConstructor @NoArgsConstructor
public class CustomerService extends BaseServiceImp <Customer, Long, CustomerRepository> {

	

	private final CustomerRepository repository;
	
	//NO CRUDS, clase para servicios personalizados
	
	public List<Customer> getLista() {
		return Arrays.asList(
				new Customer(1L, "Pepe", "pepe@gmail.com", "954 331 488", LocalDateTime.now(), 10000, true, null),//falta por poner el last value
				new Customer(2L, "Pepita", "pepita@gmail.com", "954 625 360", LocalDateTime.now(), 5, false, null)
				);		
	}
	
}
