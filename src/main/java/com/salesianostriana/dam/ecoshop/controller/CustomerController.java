package com.salesianostriana.dam.ecoshop.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.service.CustomerService;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService service;
	
	/*
	@GetMapping({"/login"})
    public String login() {
        return "login5"; 
    }
	
	@PostMapping("/login/submit")
	public String processForm (@ModelAttribute ("customer") Model model) {
		
		return "redirect:/home";
	}
	*/
	
	@GetMapping({"/", "/list"})
	public String findAll (Model model, Principal principal) {
		model.addAttribute("customers", service.findAllExceptUsername(principal.getName()));
		return "customers/list";
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/new")
	public String createForm (Model model) {
		model.addAttribute("customer", new Customer());
		return "customers/form";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/new/submit")
	public String save (@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            return "customers/form";
		}
		service.save(customer);
		return "redirect:/customers/list";
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/edit/{id}")
	public String editForm (@PathVariable Long id, Model model) {
		
		Customer customer = service.findById(id).orElse(null);
		model.addAttribute("customer", customer);
		
		return "customers/form";
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/delete/{id}")
	public String delete (@PathVariable Long id, Authentication authentication, Model model) {
		
		Optional<Customer> customer = service.findById(id);
		
		//aunque no sale en la lista de clientes, también lo podría hacer con la ruta, por lo que necesita esta 'validación' para que no haga /customers/delete/1, siendo 1 el admin y estando logueado en esa acc
		if (customer.isPresent()) {
	        String currentUsername = authentication.getName();

	        if (customer.get().getUser() != null && customer.get().getUser().getUsername().equals(currentUsername)) {
	            return "redirect:/customers/list";
	        }

	        service.delete(customer.get());
	    }
	    return "redirect:/customers/list";
	}
	
	
	
}
