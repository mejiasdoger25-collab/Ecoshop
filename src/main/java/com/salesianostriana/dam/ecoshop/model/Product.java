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
	
	@NotEmpty(message = "Se requiere el nombre del producto.")
	@Size(min = 5, max = 30, message = "El nombre del producto debe tener entre 5 y 30 caracteres.")
	private String name;
	
	@Positive(message = "El precio debe ser mayor que 0")
	private double price;
	
	@PositiveOrZero(message = "El stock no puede ser negativo")
	private int stock;
	
	@Min(value = 0, message = "El stock mínimo no puede ser negativo")
	private int minimumStock;
	
	@NotEmpty(message = "Se requiere información sobre el origen del producto.")
	@Size(max = 40, message = "El origen no puede exceder los 40 caracteres.")
	private String origin;
	
	private boolean ecoCertificate;
	
	@NotEmpty(message = "Se requiere descripción")
	@Size(max = 900, message = "La descripción no puede exceder los 900 caracteres.")
	private String description;
	
	@Future(message = "La fecha de caducidad debe ser futura.")
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
