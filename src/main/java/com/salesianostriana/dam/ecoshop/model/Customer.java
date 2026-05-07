package com.salesianostriana.dam.ecoshop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Customer {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private String email;
	private String phone;
	private LocalDateTime registrationDate;
	private double totalSpent;
	private boolean vip;
	
	//relación con pedidos
    @OneToMany(mappedBy = "customer", fetch=FetchType.EAGER)
    @Builder.Default
    private final ArrayList<Order> orders = new ArrayList<>();
}
