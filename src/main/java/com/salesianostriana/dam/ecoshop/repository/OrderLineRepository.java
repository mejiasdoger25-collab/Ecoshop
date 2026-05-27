package com.salesianostriana.dam.ecoshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.Order;
import com.salesianostriana.dam.ecoshop.model.OrderLine;
import com.salesianostriana.dam.ecoshop.model.OrderLinePK;
import com.salesianostriana.dam.ecoshop.model.Product;

@Service
public interface OrderLineRepository 
	extends JpaRepository<OrderLine, OrderLinePK>{

	
	//consultas derivadas
	List<OrderLine> findByOrder(Order order);
    List<OrderLine> findByProduct(Product product);
    
    List<OrderLine> findByOrderId(Long orderId);
	
	public List <OrderLineRepository> findAll (Long id);
	
	
	
	
	//consultas manuales
	
	//línea de pedidio en las que el pedido se igual a x, ordenada por id de la línea pedido
	@Query("""
		    SELECT ol FROM OrderLine ol 
		    WHERE ol.order.id = :orderId 
		    ORDER BY ol.id.lineNumber ASC
		    """)
		List<OrderLine> findByOrderIdV2(@Param("orderId") Long orderId);
	
}
