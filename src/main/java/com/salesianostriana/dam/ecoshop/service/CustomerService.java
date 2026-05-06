package com.salesianostriana.dam.ecoshop.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.Customer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	public List<Customer> getLista() {
		return Arrays.asList(
				new Customer(1L, "Pepe", "pepe@gmail.com", "954 331 488", LocalDateTime.now(), 10000, true),
				new Customer(2L, "Pepita", "pepita@gmail.com", "954 625 360", LocalDateTime.now(), 5, false)
				);		
	}
	
}
