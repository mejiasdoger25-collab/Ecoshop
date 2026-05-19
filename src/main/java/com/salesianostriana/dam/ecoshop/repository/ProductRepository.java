package com.salesianostriana.dam.ecoshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.ecoshop.model.Product;

@Repository
public interface ProductRepository
	extends JpaRepository<Product, Long>{

	/*
	//consultas
	public List<Product> findByNameContainingIgnoreCase(String name);
	
	public List<Product> findAllContainingIgnoreCase (String name);
	*/
}
