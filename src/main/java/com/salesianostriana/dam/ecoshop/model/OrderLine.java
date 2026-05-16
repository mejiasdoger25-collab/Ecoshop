package com.salesianostriana.dam.ecoshop.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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

	@EmbeddedId//para indicar que la pk ahora es cun campo compuesto
    private OrderLinePK id;
	
	private int amount;
	private double unitPrice;
	private double subTotal;
	
	//relación con Order
	@ManyToOne(fetch=FetchType.EAGER)
	@MapsId("orderId")//esta anotación lo que hace es indicar a jpa que el campo orderId de la pk sale automáticamente del pedido asociado, es decir, que OrderLinePK.orderId se va a rellenar usando Order.id
	@JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_orderline_order"))//el name en la bbdd es formato camel, no con mayus
	private Order order;
	
	//relación con Product
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_orderline_product"))//el name en la bbdd es formato camel, no con mayus
	private Product product;

	
}
