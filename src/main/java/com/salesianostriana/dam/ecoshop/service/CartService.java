package com.salesianostriana.dam.ecoshop.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.salesianostriana.dam.ecoshop.model.Product;
import com.salesianostriana.dam.ecoshop.repository.ProductRepository;
import com.salesianostriana.dam.ecoshop.service.base.BaseServiceImp;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService extends BaseServiceImp<Product, Long, ProductRepository> {

    private final Map<Product, Integer> products = new HashMap<>();
    private final ProductService productService;
    
    public CartService(ProductRepository repository, ProductService productService) {
        super(repository);
        this.productService = productService;
    }

    public void addProduct(Product p) {

    	//fixeo del +1 button en el line del cart, no detectaba el mismo id y creaba una nueva línea de pedido igual
        Product existing = products.keySet()
                .stream()
                .filter(prod -> prod.getId().equals(p.getId()))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            products.put(existing, products.get(existing) + 1);
        } else {
            products.put(p, 1);
        }
    }

    public void removeProduct(Product p) {
        if (products.containsKey(p)) {
            if (products.get(p) > 1) {
                products.merge(p, -1, Integer::sum);
            } else {
                products.remove(p);
            }
        }
    }

    public void removeProductById(Long id) {
        products.keySet().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .ifPresent(products::remove);
    }
    
    //para el restar -1 cant de product del cart
    public void removeOneById(Long id) {

        products.keySet().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .ifPresent(this::removeProduct);
    }
    

    public Map<Product, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    public double getTotal() {
        return products.entrySet().stream()
                .mapToDouble(entry -> productService.getEffectivePrice(entry.getKey()) * entry.getValue())//cambiado por el method lógica negocio para descuentos si es eco // amento si stock is low
                .sum();
    }

    public void clearCart() {
        products.clear();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }
}
