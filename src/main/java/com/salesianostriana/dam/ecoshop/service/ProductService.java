package com.salesianostriana.dam.ecoshop.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.Product;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProductService {

	private List<Product> productList = new ArrayList<Product>();
	
	public List<Product> getLista() {
		return Arrays.asList(
				new Product(1L, "Naranja", 3.99, 39, 10, "España", true, "Una fruta muy sabrosa", LocalDate.now(), null),//poner clave externa
				new Product(2L, "Lata reciclada", 2.99, 5, 1, "Francia", true, "Una lata hecha a base de reciclaje", null, null)
				);		
	}
	
	public void add (Product product) {
		productList.add(product);
	}
	
}
