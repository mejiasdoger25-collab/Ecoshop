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
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.model.Order;
import com.salesianostriana.dam.ecoshop.model.OrderLine;
import com.salesianostriana.dam.ecoshop.model.OrderLinePK;
import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.service.CustomerService;
import com.salesianostriana.dam.ecoshop.service.OrderService;
import com.salesianostriana.dam.ecoshop.service.ProductService;

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
	private final ProductService productService;
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
	                .orElseThrow(() -> new NoSuchElementException("Customer not found"));//con exception genérica, mejora: hacer la mía propia

	        model.addAttribute("orders",customer.getOrders());
	    }

	    return "orders/list";
	}
	
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/new")
	public String createForm(Model model, Principal principal) {

		Order order = new Order();
	    OrderLine line = new OrderLine();
	    
	    line.setId(new OrderLinePK()); //para la PK embebida
	    order.getLines().add(line);

	    model.addAttribute("order", order);
	    model.addAttribute("products", productService.findAll()); //así podemos ver todos y tiene más sentido el create order

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
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping({"/new/submit", "/edit/{id}"})
	public String save(@Valid @ModelAttribute("order") Order order,   BindingResult bindingResult,  Model model,  Principal principal, @RequestParam(required = false) Long deleteLine) {

	    if (bindingResult.hasErrors()) {
	        //poner los datos otra vez para volver al formulario
	        model.addAttribute("products", productService.findAll());
	        if (principal.getName().equals("admin")) {
	            model.addAttribute("customers", customerService.findAll());
	        } else {
	            model.addAttribute("customers", List.of(customerService.findByUsername(principal.getName()).orElseThrow()));
	        }
	        model.addAttribute("isAdmin", principal.getName().equals("admin"));
	        model.addAttribute("isEdit", order.getId() != null); 
	        return "orders/form";
	    }

	    boolean isEdit = order.getId() != null;

	    //lógica de borrado de línea de producto entera en el order edit form
	    if (deleteLine != null) {

	        order.getLines().removeIf(line ->
	                line.getId() != null
	                && line.getId().getLineNumber() != null
	                && line.getId().getLineNumber().equals(deleteLine));

	        model.addAttribute("order", order);
	        model.addAttribute("products", productService.findAll());

	        if (principal.getName().equals("admin")) {
	            model.addAttribute("customers", customerService.findAll());
	        } else {
	            Customer customer = customerService
	                    .findByUsername(principal.getName())
	                    .orElseThrow();
	            model.addAttribute("customers", List.of(customer));
	        }

	        model.addAttribute("isAdmin", principal.getName().equals("admin"));
	        model.addAttribute("isEdit", true);

	        return "orders/form";
	    }
	    
	    //asignar cliente, lógica de usuario normal
	    if (!principal.getName().equals("admin")) {
	        Customer customer = customerService.findByUsername(principal.getName()).orElseThrow();
	        order.setCustomer(customer);
	    }

	    //procesar líneas de pedido
	    if (!order.getLines().isEmpty()) {
	        for (int i = 0; i < order.getLines().size(); i++) {
	            OrderLine line = order.getLines().get(i);
	            
	            if (line.getProduct() == null || line.getProduct().getId() == null) {
	                continue;
	            }

	            Product product = productService.findById(line.getProduct().getId())
	                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

	            line.setProduct(product);
	            line.setOrder(order);
	            line.setUnitPrice(product.getPrice());
	            line.setSubTotal(product.getPrice() * line.getAmount());

	            //clave compuesta
	            if (line.getId() == null) {
	                line.setId(new OrderLinePK());
	            }

	            if (isEdit && line.getId().getOrderId() == null) {
	                line.getId().setOrderId(order.getId()); //mantener id existente en edición
	            } else if (!isEdit) {
	                line.getId().setOrderId(null); //asignación auto al hacer save en el edit
	                line.getId().setLineNumber((long) (i + 1));
	            }
	        }
	    }

	    //datos del pedido
	    if (order.getCode() == null || order.getCode().trim().isEmpty()) {
	        order.setCode("ORD-" + System.currentTimeMillis());
	    }
	    order.setStatus("PENDIENTE");
	    order.setTotal(order.getLines().stream()
	            .mapToDouble(OrderLine::getSubTotal)
	            .sum());

	    service.save(order); 

	    return "redirect:/orders/list";
	}
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, Model model, Principal principal) {
	    
	    Order order = service.findById(id).orElse(null);

	    if (order.getLines().isEmpty()) {
	        OrderLine line = new OrderLine();
	        line.setId(new OrderLinePK());
	        order.getLines().add(line);
	    }

	    model.addAttribute("order", order);
	    model.addAttribute("products", productService.findAll());
	    model.addAttribute("isEdit", true);
	    model.addAttribute("isAdmin", principal.getName().equals("admin"));

	    if (principal.getName().equals("admin")) {
	        model.addAttribute("customers", customerService.findAll());
	    } else {
	        Customer customer = customerService.findByUsername(principal.getName()).orElseThrow(() -> new NoSuchElementException("Cliente no encontrado"));
	        model.addAttribute("customers", List.of(customer));
	    }

	    return "orders/form";
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/delete/{id}")
	public String delete (@PathVariable Long id, Model model) {
		
		Order order = service.findById(id).orElseThrow(() -> new NoSuchElementException("Pedido no encontrado"));
		service.deleteById(id);
		
		return "redirect:/orders/list";
	}
	
	@PreAuthorize("hasAnyRole('USER','VIP','ADMIN')")
	@GetMapping("/details/{id}")
	public String details(@PathVariable Long id, Model model) {
	    Order order = service.findById(id).orElseThrow(() -> new NoSuchElementException("Order not found"));

	    model.addAttribute("order", order);
	    return "orders/details";
	}
}
