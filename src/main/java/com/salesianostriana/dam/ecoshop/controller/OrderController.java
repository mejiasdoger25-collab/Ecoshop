package com.salesianostriana.dam.ecoshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianostriana.dam.ecoshop.model.Order;
import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.service.CustomerService;
import com.salesianostriana.dam.ecoshop.service.OrderService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService service;
	private final CustomerService customerService;
	
	@GetMapping({"/", "/list"})
	public String findAll (Model model) {
		model.addAttribute("orders", service.findAll());
		return "orders/list";
	}
	
	@GetMapping("/new")
	public String createForm(Model model) {
		model.addAttribute("order", new Order());
		model.addAttribute("customers", customerService.findAll());
		return "orders/form";
	}
	
	@PostMapping("/new/submit")
	public String save (@ModelAttribute("order") Order order, Model model) {
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
}
