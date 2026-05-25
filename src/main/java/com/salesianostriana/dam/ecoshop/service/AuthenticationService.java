package com.salesianostriana.dam.ecoshop.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	/*
    private final CustomerRepository customerRepository;

    
    public Customer getCurrentCustomer() {						//HELPERS

        Authentication auth = SecurityContextHolder
        		.getContext()
        		.getAuthentication();

        String username = auth.getName();

        return customerRepository
                .findByUserUsername(username)
                .orElseThrow();
    }
    */
}