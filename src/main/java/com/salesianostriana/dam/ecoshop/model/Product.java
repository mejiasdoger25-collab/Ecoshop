package com.salesianostriana.dam.ecoshop.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Future;
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

	//faltan todas las validaciones de los datos para la bbdd
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty(message = "Product name is required")
	@Size(min = 5, max = 30, message = "Product name must be between 2 and 100 characters")
	private String name;
	
	@Positive(message = "Price must be greater than 0")
	private double price;
	
	@PositiveOrZero(message = "Stock cannot be negative")
	private int stock;
	
	@Min(value = 0, message = "Minimum stock cannot be negative")
	private int minimumStock;
	
	@NotEmpty(message = "Product origin is required")
	@Size(max = 30, message = "Origin cannot exceed 80 characters")
	private String origin;
	
	private boolean ecoCertificate;
	
	@NotEmpty(message = "Description is required")
	@Size(max = 900, message = "Description must be between 10 and 900 characters")
	private String description;
	
	@Future(message = "Expiration date must be in the future")
	private LocalDate expirationDate;
	
	private String image;
	
	//relación con líneas
	@OneToMany(mappedBy = "product", fetch=FetchType.EAGER)
	@Builder.Default
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final List<OrderLine> lines = new ArrayList<>();
	
	
	//relación con category
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
}
