package com.salesianostriana.dam.ecoshop.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Product {

	private String name;
	private double price;
	private int stock;
	private String origin;
	private boolean ecoCertificate;
	
}
