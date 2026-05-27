package com.salesianostriana.dam.ecoshop.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.security.user.User;

@Repository
public interface ProductRepository
	extends JpaRepository<Product, Long>{

	
	//consultas derivadas
	
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
    
    
    
    
    //consultas manuales
    
 //productos más vendidos, por cantidad total vendida
    @Query("""
        SELECT p FROM Product p 
        JOIN p.lines ol 
        GROUP BY p 
        ORDER BY SUM(ol.amount) DESC
        """)
    List<Product> findTopSellingProducts();

    //top num productos más vendidos, con paginación
    @Query("""
        SELECT p FROM Product p 
        JOIN p.lines ol 
        GROUP BY p 
        ORDER BY SUM(ol.amount) DESC
        """)
    List<Product> findTopSellingProducts(org.springframework.data.domain.Pageable pageable);

    //productos con stock crítico, por debajo del mínimo
    @Query("SELECT p FROM Product p WHERE p.stock <= p.minimumStock")
    List<Product> findLowStockProducts();

    //búsqueda de productos según 5 atbs
    @Query("""
        SELECT p FROM Product p 
        WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:origin IS NULL OR LOWER(p.origin) LIKE LOWER(CONCAT('%', :origin, '%')))
          AND (:eco IS NULL OR p.ecoCertificate = :eco)
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
        ORDER BY p.price ASC
        """)
    List<Product> searchProducts(
            @Param("name") String name,
            @Param("origin") String origin,
            @Param("eco") Boolean eco,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );

    //productos próximos a caducar, próximos 30 días
    @Query("SELECT p FROM Product p WHERE p.expirationDate IS NOT NULL AND p.expirationDate BETWEEN :now AND :limitDate")
    List<Product> findProductsNearExpiration(@Param("now") LocalDate now, @Param("limitDate") LocalDate limitDate);
}
