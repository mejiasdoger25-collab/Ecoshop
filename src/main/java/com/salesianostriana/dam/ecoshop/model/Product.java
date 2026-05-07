package com.salesianostriana.dam.ecoshop.model;

import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
	
	
	//relación con líneas
	@OneToMany(mappedBy = "product", fetch=FetchType.EAGER)
	@Builder.Default
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final ArrayList<OrderLine> lines = new ArrayList<>();
}
