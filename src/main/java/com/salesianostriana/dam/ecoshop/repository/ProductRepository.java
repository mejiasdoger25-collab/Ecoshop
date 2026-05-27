package com.salesianostriana.dam.ecoshop.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.security.user.User;

@Repository
public interface ProductRepository
	extends JpaRepository<Product, Long>{

	
	//consultas
	
	Optional<Product> findByName(String name);
	
	
	List<Product> findByEcoCertificateTrue();                    //only eco products
    List<Product> findByEcoCertificateFalse();					 //only no eco products
    
    List<Product> findByStockGreaterThan(int stock);             //only stock availible stock products
    List<Product> findByStockLessThanEqual(int stock);           //low stock products
    List<Product> findByStockLessThanEqualAndMinimumStockGreaterThanEqual(int stock, int minStock);		//>minStock && <stock
    
    List<Product> findByPriceBetween(double minPrice, double maxPrice);		//more cheaper than > x. ofertas & descuentos
    List<Product> findByPriceLessThanEqual(double maxPrice);     //more cheaper than > x. ofertas & descuentos
    
    List<Product> findByOriginContainingIgnoreCase(String origin);			//by origin
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);		//by name || description
    
    List<Product> findByExpirationDateBefore(LocalDate date);    //near to expiration date
    List<Product> findByExpirationDateAfter(LocalDate date);	//alr expired
    
    // Top productos
    List<Product> findTop8ByOrderByPriceDesc();                  //p + expensive
    List<Product> findTop12ByEcoCertificateTrueOrderByStockDesc(); //eco-p w/ more stock
}
