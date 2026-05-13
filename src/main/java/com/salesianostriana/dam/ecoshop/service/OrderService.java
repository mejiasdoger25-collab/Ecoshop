package com.salesianostriana.dam.ecoshop.service;

import java.time.LocalDateTime;
//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.model.Order;
//import com.salesianostriana.dam.ecoshop.model.OrderLine;
import com.salesianostriana.dam.ecoshop.model.OrderLine;
import com.salesianostriana.dam.ecoshop.repository.CustomerRepository;
import com.salesianostriana.dam.ecoshop.repository.OrderLineRepository;
import com.salesianostriana.dam.ecoshop.repository.OrderRepository;
import com.salesianostriana.dam.ecoshop.service.base.BaseServiceImp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Service
public class OrderService extends BaseServiceImp <Order, Long, OrderRepository>{

	public OrderService(OrderRepository repository) {
        super(repository);
    }
	
	/*
	private final OrderRepository repository;
	
	public List<Order> getLista() {
		return Arrays.asList(
				new Order(1L, "123ABC", LocalDateTime.now(), 73.99, "sent", null, new Customer(), null),//último valor por poner
				new Order(2L, "121ABA", LocalDateTime.now(), 2.50, "sent", null, new Customer(), null)
				);		
	}
	*/
	
}
