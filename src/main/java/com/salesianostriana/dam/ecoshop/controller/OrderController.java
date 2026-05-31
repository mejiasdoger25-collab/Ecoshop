package com.salesianostriana.dam.ecoshop.controller;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.model.Order;
import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.service.CustomerService;
import com.salesianostriana.dam.ecoshop.service.OrderService;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import jakarta.validation.Validator;

@Controller
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService service;
	private final CustomerService customerService;
	private final Validator validator;
	
	@PreAuthorize("hasAnyRole('USER','VIP','ADMIN')")
	@GetMapping({"/", "/list"})
	public String findAll(Model model, Principal principal) {

	    String username = principal.getName();

	    if(username.equals("admin")) {
	        model.addAttribute("orders",service.findAll());
	    } else {
	        Customer customer = customerService
	                .findByUsername(username)
	                .orElseThrow(() -> new NoSuchElementException("Customer not found"));//con exception genérica

	        model.addAttribute("orders",customer.getOrders());
	    }

	    return "orders/list";
	}
	
	
	
	
	@PreAuthorize("hasAnyRole('USER','VIP','ADMIN')")
	@GetMapping("/new")
	public String createForm(Model model, Principal principal) {
	    model.addAttribute("order",new Order());

	    if(principal.getName().equals("admin")) {
	        model.addAttribute("customers", customerService.findAll());
	    } else {
	        Customer customer = customerService
	                .findByUsername(principal.getName())
	                .orElseThrow(() -> new NoSuchElementException("Customer not found"));//con exception genérica

	        model.addAttribute("customers", List.of(customer));
	    }

	    model.addAttribute("isAdmin", principal.getName().equals("admin"));
	    
	    return "orders/form";
	}
	
	@PreAuthorize("hasAnyRole('USER','VIP','ADMIN')")
	@PostMapping("/new/submit")
	public String save (@Valid @ModelAttribute("order") Order order, BindingResult bindingResult, Model model, Principal principal) {
		if (bindingResult.hasErrors()) {

	        if(principal.getName().equals("admin")) {
	            model.addAttribute("customers",customerService.findAll());
	        } else {

	            Customer customer = customerService
	                    .findByUsername(principal.getName())
	                    .orElseThrow(() -> new NoSuchElementException("Customer not found"));//con exception genérica

	            model.addAttribute("customers", List.of(customer));
	        }

	        model.addAttribute("isAdmin", principal.getName().equals("admin"));//para pasar al form template y coger el id auto si no eres rol admin

	        return "orders/form";
	    }

		 if(!principal.getName().equals("admin")) {

		        Customer customer = customerService
		                .findByUsername(principal.getName())
		                .orElseThrow(() -> new NoSuchElementException("Customer not found"));//con exception genérica

		        order.setCustomer(customer);
		    }

		 
		//generación del code auto order
		    if (order.getCode() == null || order.getCode().trim().isEmpty()) {
		        int randomNumber = (int) (Math.random() * 900000) + 100000; //6dígitos
		        order.setCode("ORD-" + randomNumber);
		    }

		 
		    service.save(order);

		    return "redirect:/orders/list";
		}
	
	
	
	
	@GetMapping("/edit/{id}")
	public String editForm (@PathVariable Long id, Model model) {
		
		Order order = service.findById(id).orElse(null);
		model.addAttribute("order", order);
		model.addAttribute("customers", customerService.findAll());
		
		return "/orders/form";
	}
	
	
	@GetMapping("/delete/{id}")
	public String delete (@PathVariable Long id, Model model) {
		
		Optional<Order> order = service.findById(id);
		
		if(order.isPresent())
			service.deleteById(id);
		
		return "redirect:/orders/list";
	}
	
	
	@GetMapping("/details/{id}")
	public String details(@PathVariable Long id, Model model) {
	    Order order = service.findById(id).orElseThrow(() -> new NoSuchElementException("Order not found"));

	    model.addAttribute("order", order);
	    return "orders/details";
	}
}
