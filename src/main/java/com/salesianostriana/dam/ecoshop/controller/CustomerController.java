package com.salesianostriana.dam.ecoshop.controller;

import org.springframework.stereotype.Controller;

import com.salesianostriana.dam.ecoshop.service.CustomerService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService service;
}
