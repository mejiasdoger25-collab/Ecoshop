package com.salesianostriana.dam.ecoshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.OrderLine;
import com.salesianostriana.dam.ecoshop.model.OrderLinePK;

@Service
public interface OrderLineRepository 
	extends JpaRepository<OrderLine, OrderLinePK>{

	/*
	//consultas
	public List <OrderLineRepository> findById (Long id);
	
	public List <OrderLineRepository> findAll (Long id);
	*/
}
