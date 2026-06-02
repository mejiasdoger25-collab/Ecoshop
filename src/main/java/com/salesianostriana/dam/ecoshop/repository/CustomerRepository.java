package com.salesianostriana.dam.ecoshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.ecoshop.model.Customer;

@Repository
public interface CustomerRepository 
	extends JpaRepository<Customer, Long>{

	//consultas derivadas
	Optional<Customer> findByUserUsername(String username);
	
	//consultas derivadas
    List<Customer> findByVipTrue();                              //para los vips
    List<Customer> findByVipFalse();                             //para los no vips
    List<Customer> findByNameContainingIgnoreCase(String name);  //por name
    List<Customer> findByTotalSpentGreaterThanEqual(double amount); //customers que han gastado x o >x
    List<Customer> findByBalanceGreaterThan(double balance);     //clientes con saldo, aunque por las validations tengo puesto que no puedes crearte una acc con negative balance
    List<Customer> findByRegistrationDateAfter(java.time.LocalDateTime date);	//registrados a partir de x
    
    boolean existsByEmail(String email);                         //emails no dobles
    boolean existsByPhone(String phone);						//phones no dobles
    
    //sorted
    List<Customer> findTop10ByOrderByTotalSpentDesc();                  //clientes que más gan gastado top 10
	
    
    
    
	    //consultas manuales
    
    	//customers, ordenados por dinero gastado
	    @Query("""
	    	    SELECT c FROM Customer c 
	    	    ORDER BY c.totalSpent DESC
	    	    """)
	    	List<Customer> findTopCustomers();

	    //customers que han gastado > x
    	@Query("""
    	    SELECT c FROM Customer c 
    	    WHERE c.totalSpent > :amount 
    	    ORDER BY c.totalSpent DESC
    	    """)
    	List<Customer> findCustomersByTotalSpentGreaterThan(@Param("amount") double amount);

    	//media gastada entre todos los customers
    	@Query("SELECT AVG(c.totalSpent) FROM Customer c")
    	Double getAverageCustomerSpending();
    
    	
    	
    	
    	
    	//para hacer el findall de los customers pero sin el que está inciado
    	@Query("""
    		    SELECT c
    		    FROM Customer c
    		    LEFT JOIN FETCH c.user u
    		    WHERE (u IS NULL OR u.username <> :username)
    		""")
    		List<Customer> findAllExceptUsername(@Param("username") String username);
    	
    	
    	
    	
    	
    	
    	
    	//dashboard
    	@Query("""
    		       SELECT c
    		       FROM Customer c
    		       JOIN Order o ON o.customer.id = c.id
    		       GROUP BY c
    		       ORDER BY SUM(o.total) DESC
    		       """)
    		List<Customer> findTopCustomersByRevenue();
    	
    	
    	
    	@Query("""
    		       SELECT COALESCE(SUM(o.total),0)
    		       FROM Order o
    		       WHERE o.customer.id = :customerId
    		       """)
    		Double getCustomerTotalSpent(@Param("customerId") Long customerId);
}
