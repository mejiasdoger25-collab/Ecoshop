package com.salesianostriana.dam.ecoshop.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLinePK implements Serializable {

	@NotNull(message = "Se requiere el ID del pedido.")
    private Long orderId;
	
	@NotNull(message = "Se requiere el número de línea.")
	@Min(value = 1, message = "El número de línea debe ser mayor que 0.")
    private Long lineNumber;

}