package com.salesianostriana.dam.ecoshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ForeignKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@AllArgsConstructor @NoArgsConstructor
@Builder
public class OrderLine {

	@Id
	@GeneratedValue
	private Long id;
	
	private int amount;
	private double unitPrice;
	private double subTotal;
	
	//relación con Order
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey (name="order_id"))//el name en la bbdd es formato camel, no con mayus
	private Order order;
	
	//relación con Product
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey (name="product_id"))//el name en la bbdd es formato camel, no con mayus
	private Product product;

	
}
