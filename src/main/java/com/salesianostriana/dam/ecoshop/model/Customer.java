package com.salesianostriana.dam.ecoshop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class Customer {

	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty(message = "Customer name is required")
	@Size(min = 2, max = 100, message = "Customer name must be between 2 and 100 characters")
	private String name;
	
	@NotEmpty(message = "Email is required")
	@Email(message = "Invalid email format")
	@Size(max = 120, message = "Email cannot exceed 120 characters")
	private String email;
	
	@NotEmpty(message = "Phone number is required")
	@Size(min = 9, max = 9, message = "Phone number must contain 9 digits")
	private String phone;
	
	@NotNull(message = "Registration date is required")
	private LocalDateTime registrationDate;
	
	@PositiveOrZero(message = "Total spent cannot be negative")
	private double totalSpent;
	
	private boolean vip;
	
	@PositiveOrZero(message = "Balance cannot be negative")
	private double balance;
	
	//relación con pedidos
    @OneToMany(mappedBy = "customer", fetch=FetchType.EAGER)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final List<Order> orders = new ArrayList<>();
}
