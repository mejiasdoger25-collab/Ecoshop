package com.salesianostriana.dam.ecoshop.model;

import java.time.LocalDateTime;

//import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Order {

	private String code;
	private LocalDateTime date;
	private double total;
	
}
