package com.salesianostriana.dam.ecoshop.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.service.CartService;

import lombok.RequiredArgsConstructor;

@ControllerAdvice	//a nivel global, yo lo voy a usar para hacer el conteo de products/orderlines que tiene el cart
@RequiredArgsConstructor
public class AdviceController {

	
	private final CartService cartService;

    @ModelAttribute("cartItems")
    public Map<Product, Integer> cartItems() {
        return cartService.getProductsInCart();
    }

    @ModelAttribute("cartCount")
    public int cartCount() {
        return cartService.getProductsInCart().values().stream()
                .mapToInt(Integer::intValue)
                .sum(); //suma total de unidades no solo productos distintos, es decir, si tienes 3 cantidad del mismo producto, mustra 3, no 1
    }

    @ModelAttribute("cartTotal")
    public double cartTotal() {
        return cartService.getTotal();
    }
}