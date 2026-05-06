package com.salesianostriana.dam.ecoshop.service;

//import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.Customer;

@Service
public class CustomerService {

	public List<Customer> getLista() {
		return Arrays.asList(
				new Customer("Pepe", "pepe@gmail.com", "954 331 488"),
				new Customer("Pepita", "pepita@gmail.com", "954 625 360")
				);		
	}
	
}
