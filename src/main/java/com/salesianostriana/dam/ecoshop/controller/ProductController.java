package com.salesianostriana.dam.ecoshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.service.ProductService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@RequiredArgsConstructor 
public class ProductController {

	private final ProductService service;
	
	@GetMapping({"/", "/home"})
    public String home() {
        return "home"; 
    }
	
	
	
	
	
	
	//test-1st-form
	@GetMapping("/newCustomer")
	public String newCustomer (Model model) {
		model.addAttribute("product", new Product());
		return "form";
	}
	
	@PostMapping("/newCustomer/submit")
	public String newCustomerSubmit (@ModelAttribute("produt") Product product) {
		service.add(product);
		return "redirect:/newCustomer";
	}
	
	
}
