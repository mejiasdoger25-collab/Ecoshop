package com.salesianostriana.dam.ecoshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesianostriana.dam.ecoshop.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    
    @Query("""
    	       SELECT c
    	       FROM Category c
    	       LEFT JOIN c.products p
    	       LEFT JOIN p.lines ol
    	       GROUP BY c
    	       ORDER BY COALESCE(SUM(ol.amount),0) DESC
    	       """)
    	List<Category> findCategoriesBySalesDesc();
    
    @Query("""
    	       SELECT c
    	       FROM Category c
    	       LEFT JOIN c.products p
    	       LEFT JOIN p.lines ol
    	       GROUP BY c
    	       ORDER BY COALESCE(SUM(ol.amount),0) ASC
    	       """)
    	List<Category> findCategoriesBySalesAsc();
    
    
    //para el conteo de cada category
    @Query("""
    	       SELECT COALESCE(SUM(ol.amount),0)
    	       FROM OrderLine ol
    	       WHERE ol.product.category.id = :categoryId
    	       """)
    	Long getUnitsSoldByCategory(Long categoryId);
}