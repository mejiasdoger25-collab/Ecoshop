package com.salesianostriana.dam.ecoshop.controller;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.service.ProductService;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@RequiredArgsConstructor 
@RequestMapping("/products")
public class ProductController {

	private final ProductService service;
	
	/*
	@GetMapping({"/", "/home"})
    public String home() {
        return "home"; 
    }
	
	//test-1st-form
	@GetMapping("/newProduct")
	public String newCustomer (Model model) {
		model.addAttribute("product", new Product());
		return "newCustomerForm";
	}
	
	@PostMapping("/newProduct/submit")
	public String newCustomerSubmit (@ModelAttribute("produt") Product product) {
		service.save(product);
		return "redirect:/newCustomer";
	}
	*/
	
	@GetMapping({"/", "/list"})
	public String findAll (Model model) {
		model.addAttribute("products", service.findAll());
		return "products/list";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/new")
	public String createForm(Model model) {
		model.addAttribute("product", new Product());
		return "products/form";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/new/submit")
	public String save (@Valid @ModelAttribute("product") Product product, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            return "products/form";
		}
		service.save(product);
		return "redirect:/products/list";
	}
	
	
	
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/edit/{id}")
	public String editForm (@PathVariable Long id, Model model) {
		
		Product product = service.findById(id).orElse(null);
		model.addAttribute("product", product);
		
		//se reutiliza el form de create new products
		return "products/form";
	}
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/delete/{id}")
	public String delete (@PathVariable Long id, Model model) {
		
		Optional<Product> product = service.findById(id);
		
		if(product.isPresent()) 
			service.delete(product.get());
		
		return "redirect:/products/list";
	}
	
}
