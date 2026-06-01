package com.salesianostriana.dam.ecoshop.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianostriana.dam.ecoshop.repository.CategoryRepository;
import com.salesianostriana.dam.ecoshop.repository.CustomerRepository;
import com.salesianostriana.dam.ecoshop.repository.OrderRepository;
import com.salesianostriana.dam.ecoshop.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class DashboardController {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model){

        model.addAttribute("productsCount", productRepository.count());
        model.addAttribute("customersCount", customerRepository.count());
        model.addAttribute("ordersCount", orderRepository.count());
        model.addAttribute("revenue", orderRepository.getTotalRevenue());

        model.addAttribute("lowStockProducts", productRepository.findLowStockProducts()
                        .stream()
                        .limit(5)
                        .toList());

        
        //clientes bloque
        var topCustomers = customerRepository.findTopCustomersByRevenue()
                .stream()
                .limit(5)
                .toList();

        Map<Long, Double> customerTotals = new HashMap<>();
        for (var customer : topCustomers) {
            customerTotals.put( customer.getId(), customerRepository.getCustomerTotalSpent(customer.getId()));
        }

        model.addAttribute("topCustomers", topCustomers);
        model.addAttribute("customerTotals", customerTotals);

        
        var bestCategory = categoryRepository.findCategoriesBySalesDesc()
                        .stream()
                        .findFirst()
                        .orElse(null);
        

        var worstCategory = categoryRepository.findCategoriesBySalesAsc()
                        .stream()
                        .findFirst()
                        .orElse(null);
        
        
        
        //para el conteo de each products en cada category
        model.addAttribute("bestCategory", bestCategory);
        model.addAttribute("worstCategory", worstCategory);

        model.addAttribute("bestCategoryUnits", bestCategory != null ? categoryRepository.getUnitsSoldByCategory(bestCategory.getId()) : 0);
        model.addAttribute( "worstCategoryUnits", worstCategory != null ? categoryRepository.getUnitsSoldByCategory(worstCategory.getId()) : 0);
        
        return "/auth/dashboard";
    }
}