package com.salesianostriana.dam.ecoshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.Order;
import com.salesianostriana.dam.ecoshop.model.OrderLine;
import com.salesianostriana.dam.ecoshop.model.OrderLinePK;
import com.salesianostriana.dam.ecoshop.model.Product;

@Service
public interface OrderLineRepository 
	extends JpaRepository<OrderLine, OrderLinePK>{

	
	//consultas
	List<OrderLine> findByOrder(Order order);
    List<OrderLine> findByProduct(Product product);
    
    List<OrderLine> findByOrderId(Long orderId);
	
	public List <OrderLineRepository> findAll (Long id);
	
	
	
}
