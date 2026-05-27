package com.salesianostriana.dam.ecoshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.ecoshop.model.Customer;

@Repository
public interface CustomerRepository 
	extends JpaRepository<Customer, Long>{

	//consultaS
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
    List<Customer> findTop10ByTotalSpentDesc();                  //clientes que más gan gastado top 10
	
}
