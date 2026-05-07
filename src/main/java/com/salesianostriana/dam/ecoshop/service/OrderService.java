package com.salesianostriana.dam.ecoshop.service;

import java.time.LocalDateTime;
//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.model.Order;
//import com.salesianostriana.dam.ecoshop.model.OrderLine;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OrderService {

	public List<Order> getLista() {
		return Arrays.asList(
				new Order(1L, "123ABC", LocalDateTime.now(), 73.99, "sent", null, new Customer(), null),//último valor por poner
				new Order(2L, "121ABA", LocalDateTime.now(), 2.50, "sent", null, new Customer(), null)
				);		
	}
	
}
