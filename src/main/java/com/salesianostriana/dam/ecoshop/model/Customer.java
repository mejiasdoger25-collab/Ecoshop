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
public class Customer {

	private String name;
	private String email;
	private String phone;
}
