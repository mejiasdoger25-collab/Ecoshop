package com.salesianostriana.dam.ecoshop.service;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.salesianostriana.dam.ecoshop.model.Product;

import com.salesianostriana.dam.ecoshop.repository.ProductRepository;
import com.salesianostriana.dam.ecoshop.service.base.BaseServiceImp;



@Service
public class ProductService extends BaseServiceImp <Product, Long, ProductRepository>{

	
	private final ProductRepository productRepository;
	private final List<Product> productList = new ArrayList<Product>();

	
	public ProductService(ProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }
	
	
	//lógica de negocio
	
	//descuento del 10% a los productos que tienen ecoCertificate = true, aumento 10% si el stock es bajo
	public double getEffectivePrice(Product product) {
		double price = product.getPrice();

	    if(product.isEcoCertificate()) {
	        price *= 0.90;
	    }

	    if(product.getStock() < product.getMinimumStock()) {
	        price *= 1.10;
	    }

	    
	    //para elegir el de la oferta diaria con llamada al method
	    if(product.equals(getProductOfTheDay())) {
	        price *= 0.85;
	    }
	    
	    return price;
    }
	
	
	//para randomizar las cards products cada vez que se le da a total category
	public List<Product> findAllRandom() {

	    List<Product> products = productRepository.findAll();

	    Collections.shuffle(products);

	    return products;
	}
	
	
	//para randomizar las cards products cada vez que se le da a total category V2, manteniendo la paginación
	public Page<Product> findAllRandomPaged(Pageable pageable) {

	    List<Product> products = productRepository.findAll();

	    Collections.shuffle(products);

	    int start = (int) pageable.getOffset();
	    int end = Math.min(start + pageable.getPageSize(), products.size());

	    List<Product> pageContent = products.subList(start, end);

	    return new PageImpl<>(pageContent, pageable, products.size());
	}
	
	//para la ""categoría"" (filtro) de products no eco en el sidebar
	public Page<Product> getNonEcoProducts(Pageable pageable) {
	    return productRepository.findByEcoCertificateFalse(pageable);
	}
	
	//para la ""categoría"" (filtro) de products ofertas en el sidebar 
	public Page<Product> getDiscountProducts(Pageable pageable) {

	    List<Product> products = findAll().stream()
	            .filter(product -> getEffectivePrice(product) < product.getPrice()).toList();

	    int start = (int) pageable.getOffset();
	    int end = Math.min(start + pageable.getPageSize(), products.size());

	    return new PageImpl<>(
	            products.subList(start, end),
	            pageable,
	            products.size()
	    );
	}
	
	//para la ""categoría"" (filtro) de products stock bajo en el sidebar 
	public Page<Product> getLowStockProducts(Pageable pageable) {

	    List<Product> products = findAll().stream()
	            .filter(product ->
	                product.getStock() < product.getMinimumStock()
	            )
	            .toList();

	    int start = (int) pageable.getOffset();
	    int end = Math.min(start + pageable.getPageSize(), products.size());

	    return new PageImpl<>(
	            products.subList(start, end),
	            pageable,
	            products.size()
	    );
	}
	
	//para el objt diario de oferta random
	public Product getProductOfTheDay() {

	    List<Product> products = findAll();

	    if(products.isEmpty())
	        return null;

	    int day = LocalDate.now().getDayOfYear();

	    return products.get(day % products.size());
	}
	
	
	
}
