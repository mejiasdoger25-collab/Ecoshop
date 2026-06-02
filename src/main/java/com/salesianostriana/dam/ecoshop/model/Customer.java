package com.salesianostriana.dam.ecoshop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.salesianostriana.dam.ecoshop.security.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
	
	@NotEmpty(message = "Se requiere el nombre del cliente.")
	@Size(min = 2, max = 30, message = "El nombre del cliente debe tener entre 2 y 100 caracteres.")
	private String name;
	
	@NotEmpty(message = "Se requiere correo electrónico")
	@Email(message = "Formato de correo electrónico no válido")
	@Size(max = 320, message = "El correo electrónico no puede exceder los 120 caracteres.")
	private String email;
	
	@NotEmpty(message = "Se requiere número de teléfono.")
	@Pattern( regexp = "^[0-9]{9}$")
	private String phone;
	
	//@NotNull(message = "Registration date is required")
	private LocalDateTime registrationDate;
	
	//@PositiveOrZero(message = "Total spent cannot be negative")
	private double totalSpent;
	
	private boolean vip;
	
	//@PositiveOrZero(message = "Balance cannot be negative")
	private double balance;
	
	//relación con pedidos
	@OneToMany(mappedBy = "customer",  fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)           
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final List<Order> orders = new ArrayList<>();
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
