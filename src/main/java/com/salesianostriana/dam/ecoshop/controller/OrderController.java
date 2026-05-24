package com.salesianostriana.dam.ecoshop.controller;

import java.security.Principal;
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

@Controller
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService service;
	private final CustomerService customerService;
	
	@PreAuthorize("hasAnyRole('USER','VIP','ADMIN')")
	@GetMapping({"/", "/list"})
	public String findAll(Model model, Principal principal) {

	    String username = principal.getName();

	    if(username.equals("admin")) {
	        model.addAttribute(
	                "orders",
	                service.findAll()
	        );

	    } else {

	        Customer customer = customerService
	                .findByUsername(username)
	                .orElse(null);

	        model.addAttribute(
	                "orders",
	                customer.getOrders()
	        );
	    }

	    return "orders/list";
	}
	
	@GetMapping("/new")
	public String createForm(Model model) {
		model.addAttribute("order", new Order());
		model.addAttribute("customers", customerService.findAll());
		return "orders/form";
	}
	
	@PostMapping("/new/submit")
	public String save (@Valid @ModelAttribute("order") Order order, Model model, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            return "orders/form";
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
}
