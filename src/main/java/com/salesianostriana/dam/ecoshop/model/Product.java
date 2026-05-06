package com.salesianostriana.dam.ecoshop.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Product {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private double price;
	private int stock;
	private int minimumStock;
	private String origin;
	private boolean ecoCertificate;
	private String description;
	private LocalDate expirationDate;
	
}
