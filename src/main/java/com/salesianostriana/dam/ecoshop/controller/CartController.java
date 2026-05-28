package com.salesianostriana.dam.ecoshop.controller;

import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.model.Order;
import com.salesianostriana.dam.ecoshop.model.OrderLine;
import com.salesianostriana.dam.ecoshop.model.OrderLinePK;
import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.service.CustomerService;
import com.salesianostriana.dam.ecoshop.service.OrderService;
import com.salesianostriana.dam.ecoshop.service.ProductService;
import com.salesianostriana.dam.ecoshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderService orderService;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("products", cartService.getProductsInCart());
        model.addAttribute("total", cartService.getTotal());
        return "cart";
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id) {
        productService.findById(id).ifPresent(cartService::addProduct);
        return "redirect:/products/list";	//add success messge
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartService.removeProductById(id);
        return "redirect:/cart";	//tras borrar una cantidad de una línea o la línea entera si solo hay una línea, mostrar un mensaje de éxito
    }

    @GetMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/products/list";	//add: success message / invalid feedback
    }

    @PostMapping("/checkout")
    public String checkout(Principal principal) {

        if (cartService.isEmpty()) {
            return "/products/list";
        }

        String username = principal.getName();
        Customer customer = customerService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        //crear el pedido
        Order order = Order.builder()
                .code("ORD-" + System.currentTimeMillis())
                .shippingDate(LocalDate.now().plusDays(3))
                .status("PENDING")
                .customer(customer)
                .total(cartService.getTotal())
                .build();

        order = orderService.save(order);

        //crear las líneas de pedido
        Map<Product, Integer> cartItems = cartService.getProductsInCart();
        int lineNumber = 1;

        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            OrderLine line = OrderLine.builder()
                    .id(new OrderLinePK(order.getId(), (long) lineNumber++))
                    .amount(quantity)
                    .unitPrice(product.getPrice())
                    .subTotal(product.getPrice() * quantity)
                    .order(order)
                    .product(product)
                    .build();

            order.getLines().add(line);
        }

        orderService.save(order);
        cartService.clearCart();

        return "redirect:/orders/list";
    }
}