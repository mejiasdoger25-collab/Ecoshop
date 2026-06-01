package com.salesianostriana.dam.ecoshop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        model.addAttribute("topCustomers", customerRepository
                        .findTop10ByOrderByTotalSpentDesc()
                        .stream()
                        .limit(5)
                        .toList());

        return "/auth/dashboard";
    }
}