package com.salesianostriana.dam.ecoshop.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.ForeignKey;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@AllArgsConstructor @NoArgsConstructor
@Builder
public class OrderLine {

	@EmbeddedId//para indicar que la pk ahora es cun campo compuesto
    private OrderLinePK id;
	
	@Min(value = 1, message = "La cantidad debe ser al menos 1")	
	private int amount;
	
	@Positive(message = "El precio unitario debe ser mayor que 0")
	private double unitPrice;
	
	@PositiveOrZero(message = "El subtotal no puede ser negativo.")
	private double subTotal;
	
	//relación con Order
	@ManyToOne(fetch=FetchType.EAGER)
	@MapsId("orderId")//esta anotación lo que hace es indicar a jpa que el campo orderId de la pk sale automáticamente del pedido asociado, es decir, que OrderLinePK.orderId se va a rellenar usando Order.id
	@JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_orderline_order"))//el name en la bbdd es formato camel, no con mayus
	@NotNull(message = "Order line must belong to an order")
	private Order order;
	
	//relación con Product
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_orderline_product"))//el name en la bbdd es formato camel, no con mayus
	@NotNull(message = "Product is required")
	private Product product;

	
}
