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

    public CartService(ProductRepository repository) {
        super(repository);
    }

    public void addProduct(Product p) {
        products.merge(p, 1, Integer::sum);
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
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public void clearCart() {
        products.clear();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }
}
