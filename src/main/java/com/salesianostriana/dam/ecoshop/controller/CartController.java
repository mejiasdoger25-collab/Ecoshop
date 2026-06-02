package com.salesianostriana.dam.ecoshop.controller;

import com.salesianostriana.dam.ecoshop.data.DataSeed;
import com.salesianostriana.dam.ecoshop.exception.InsufficientStockException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final DataSeed dataSeed;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("products", cartService.getProductsInCart());
        model.addAttribute("total", cartService.getTotal());
        model.addAttribute("productService", productService);//para hacer la llamada en el front thymeleaf y así mostrar el price's product con el desc de la lógica de negocio doble, aumento y descuento
        return "cart";
    }

    @GetMapping("/add/{id}")//cambiado para que hacer el redirect en función del template que haces add, si haces desde el list te quedas ahí, si lo haces desde la línea de pedido, te quedas en el cart
    public String addToCart( @PathVariable Long id, @RequestParam(defaultValue = "/products/list") String redirect) {

        productService.findById(id)
                .ifPresent(cartService::addProduct);

        return "redirect:" + redirect;
    }

    
    
    //borrar todo
    @GetMapping("/remove/{id}")//versión más eficaz del delete
    public String removeFromCart(@PathVariable Long id) {
    	/*
    	productService.findById(id).ifPresent(product -> cartService.removeProductById(id));
    	*/
    	cartService.removeProductById(id);
    
        return "redirect:/cart";	//tras borrar una cantidad de una línea o la línea entera si solo hay una línea, mostrar un mensaje de éxito
    }

   //borrar 1 product
    @GetMapping("/removeOne/{id}")
    public String removeOneFromCart(@PathVariable Long id) {

        cartService.removeOneById(id);

        return "redirect:/cart";
    }
    
    
    
    @GetMapping("/clear")	//versión terminada más eficiente para las consutas
    public String clearCart() {
    	/* v1
        cartService.clearCart();
        */
        
        //v2
        java.util.Map<Product, Integer> cart = cartService.getProductsInCart();
        if (cart != null)
            cartService.clearCart();

        return "redirect:/cart";	//add: success message / invalid feedback
    }

    @PostMapping("/checkout")
    public String checkout(Principal principal, RedirectAttributes redirectAttributes) {

    	try {
    		
	        if (cartService.isEmpty()) {
	            return "redirect:/products/list";
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
	            //para cancelar la compra si tienes más cantidad de proeucts en el cart que el stock que tiene la tienda
	            if (quantity > product.getStock()) {
	                throw new InsufficientStockException("No hay suficiente stock para "+ product.getName()+ ". Disponible: "+ product.getStock());
	            }
	            
	            OrderLine line = OrderLine.builder()
	                    .id(new OrderLinePK(order.getId(), (long) lineNumber++))
	                    .amount(quantity)
	                    .unitPrice(product.getPrice())
	                    .subTotal(product.getPrice() * quantity)
	                    .order(order)
	                    .product(product)
	                    .build();
	
	            order.getLines().add(line);
	            product.setStock( product.getStock() - quantity);
	
	            productService.save(product);
	        }
	
	        orderService.save(order);
	        cartService.clearCart();
	
	        return "redirect:/orders/list";
	        
	        
    	} catch (InsufficientStockException e) {
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return "redirect:/cart";
       }
    }
}