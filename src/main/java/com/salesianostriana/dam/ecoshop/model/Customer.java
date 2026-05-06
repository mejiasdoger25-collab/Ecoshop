package com.salesianostriana.dam.ecoshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Customer {

	private String name;
	private String email;
	private String phone;
}
