package com.salesianostriana.dam.ecoshop.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Order {

	private String code;
	private LocalDateTime shippingDate;
	private double total;
	private String status;
	private LocalDateTime returnDate;
	
}
