package com.salesianostriana.dam.ecoshop.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.OrderLine;
import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.repository.CustomerRepository;
import com.salesianostriana.dam.ecoshop.repository.OrderLineRepository;
import com.salesianostriana.dam.ecoshop.repository.ProductRepository;
import com.salesianostriana.dam.ecoshop.service.base.BaseServiceImp;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Service
public class ProductService extends BaseServiceImp <Product, Long, ProductRepository>{

	
	private final ProductRepository productRepository;
	private final List<Product> productList = new ArrayList<Product>();

	
	public ProductService(ProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }
	
	
	
	/*
	public List<Product> getLista() {
		return Arrays.asList(
				new Product(1L, "Naranja", 3.99, 39, 10, "España", true, "Una fruta muy sabrosa", LocalDate.now(), null),//poner clave externa
				new Product(2L, "Lata reciclada", 2.99, 5, 1, "Francia", true, "Una lata hecha a base de reciclaje", null, null)
				);		
	}
	
	public void add (Product product) {
		productList.add(product);
	}
	
	*/
	
	@PostConstruct
	public void initUsers() {

	    if (productRepository.count() == 0) {

	    	productRepository.save(
	                Product.builder()
	                       	.name("Naranja")
	                       	.price(2)
	                       	.stock(10)
	                       	.minimumStock(5)
	                       	.origin("Fuengirola")
	                       	.ecoCertificate(true)
	                       	.description("Una misteriosa fruta de dudosa procedencia")
	                       	.expirationDate(LocalDate.now().plusWeeks(1))
	                       	//.lines(null) builder.default
	                        .build()
	        );

	    	productRepository.save(
	    			Product.builder()
		    				//.id(002L) auto
		                   	.name("Botella")
		                   	.price(1.20)
		                   	.stock(5000)
		                   	.minimumStock(100)
		                   	.origin("Madird")
		                   	.ecoCertificate(true)
		                   	.description("Realizada con material reciclado")
		                   	.expirationDate(null)//nunca
		                   	//.lines(null) builder.default
		                    .build()
	    	);

	    	productRepository.save(
	    			Product.builder()
		    				//.id(003L) auto
		                   	.name("Teléfono")
		                   	.price(500)
		                   	.stock(10)
		                   	.minimumStock(2)
		                   	.origin("China")
		                   	.ecoCertificate(false)
		                   	.description("Un dispositivo con tecnología punta")
		                   	.expirationDate(null)//nunca
		                   	//.lines(null) builder.default
		                    .build()
	    	);

	    }

	}
	
}
