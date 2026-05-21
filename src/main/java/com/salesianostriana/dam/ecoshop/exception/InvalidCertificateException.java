package com.salesianostriana.dam.ecoshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCertificateException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		//constructores para poder escribir
		// constructor sin argumentos 
		public InvalidCertificateException() {
		super();
		} 

		// constructor con un string como argumento 
		public InvalidCertificateException(String additionalInfo) {
		super(additionalInfo); 
		}
	
}
