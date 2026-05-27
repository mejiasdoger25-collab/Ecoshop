package com.salesianostriana.dam.ecoshop.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.model.Order;

@Repository
public interface OrderRepository 
	extends JpaRepository<Order, Long>{

	
	//consultas
	public List <Order> findById ();
	
	public List <Order> findAll ();
	
	
	List<Order> findByCustomer(Customer customer);
    
    List<Order> findByStatus(String status);
    List<Order> findByStatusContainingIgnoreCase(String status);
    
    List<Order> findByShippingDateAfter(LocalDate date);
    List<Order> findByShippingDateBetween(LocalDate start, LocalDate end);
    
    List<Order> findByTotalGreaterThanEqual(double amount);
    
    //orders de un customer sorted by fecha
    List<Order> findByCustomerOrderByShippingDateDesc(Customer customer);
    List<Order> findByCustomerIdOrderByShippingDateDesc(Long customerId);
    
    
}
