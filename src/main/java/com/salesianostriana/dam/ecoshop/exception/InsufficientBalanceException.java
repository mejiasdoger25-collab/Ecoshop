package com.salesianostriana.dam.ecoshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientBalanceException extends RuntimeException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		//constructores para poder escribir
		// constructor sin argumentos 
		public InsufficientBalanceException() {
		super();
		} 

		// constructor con un string como argumento 
		public InsufficientBalanceException(String additionalInfo) {
		super(additionalInfo); 
		}
	
		
}
