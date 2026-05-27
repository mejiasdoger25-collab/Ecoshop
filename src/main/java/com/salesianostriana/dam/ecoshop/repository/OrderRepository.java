package com.salesianostriana.dam.ecoshop.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.model.Order;

@Repository
public interface OrderRepository 
	extends JpaRepository<Order, Long>{

	
	//consultas derivadas
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
    
    
    
	    //consultas manuales
	    
	    //por customer id, ordenado por fecha de envío
	    @Query("""
	    	    SELECT o FROM Order o 
	    	    WHERE o.customer.id = :customerId 
	    	    ORDER BY o.shippingDate DESC
	    	    """)
	    	List<Order> findOrdersByCustomer(@Param("customerId") Long customerId);

	    //por status del p, ordenado por fecha de envío
    	@Query("""
    	    SELECT o FROM Order o 
    	    WHERE o.status = :status 
    	    ORDER BY o.shippingDate DESC
    	    """)
    	List<Order> findOrdersByStatus(@Param("status") String status);

    	//por status completado del p
    	@Query("SELECT SUM(o.total) FROM Order o WHERE o.status = 'COMPLETED'")
    	Double getTotalRevenue();

    	//entre x e y por fecha de envío, ordenado por fecha de envío
    	@Query("""
    	    SELECT o FROM Order o 
    	    WHERE o.shippingDate BETWEEN :startDate AND :endDate
    	    ORDER BY o.shippingDate DESC
    	    """)
    	List<Order> findOrdersBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
