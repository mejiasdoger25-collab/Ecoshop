package com.salesianostriana.dam.ecoshop.service;

//import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.Product;


@Service
public class ProductService {

	public List<Product> getLista() {
		return Arrays.asList(
				new Product("Naranja", 3.99, 39, "España", true),
				new Product("Lata reciclada", 2.99, 5, "Francia", true)
				);		
	}
}
