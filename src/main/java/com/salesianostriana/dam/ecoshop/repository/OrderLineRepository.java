package com.salesianostriana.dam.ecoshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.OrderLine;
import com.salesianostriana.dam.ecoshop.model.OrderLinePK;

@Service
public interface OrderLineRepository 
	extends JpaRepository<OrderLine, OrderLinePK>{

}
