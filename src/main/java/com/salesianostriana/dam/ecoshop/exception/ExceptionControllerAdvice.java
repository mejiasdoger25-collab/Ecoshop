package com.salesianostriana.dam.ecoshop.exception;

import java.util.NoSuchElementException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

	//Own exception
    @ExceptionHandler(InsufficientStockException.class)
    public String handleInsufficientStock(InsufficientStockException ex, Model model) {
        model.addAttribute("tittleError", "Insufficient stock");
        model.addAttribute("messageError", ex.getMessage());
        return "error/error"; 
    }
    
    //Own exception 
    @ExceptionHandler(InvalidCertificateException.class)
    public String handleInvalidCertificate(InvalidCertificateException ex, Model model) {
        model.addAttribute("errorTitulo", "Invalid certificate");
        model.addAttribute("messageError", ex.getMessage());
        return "error/error"; 
    }
    
  //Own exception 
    @ExceptionHandler(InvalidCertificateException.class)
    public String handleInsufficientBalance(InsufficientBalanceException ex, Model model) {
        model.addAttribute("tittleError", "Insufficient balance");
        model.addAttribute("messageError", ex.getMessage());
        return "error/error"; 
    }
    

    //Api java exception 
    //Find an Id that doesnt exists
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(NoSuchElementException ex, Model model) {
        model.addAttribute("tittleError", "Element not found");
        model.addAttribute("messageError", "The requested product does not exist in our database.");
        return "error/error";
    }

    //Api java exception
    //Illegal or invalid argument in business logic
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        model.addAttribute("tittleError", "Invalid argument");
        model.addAttribute("messageError", ex.getMessage());
        return "error/error";
    }
	
}
