package com.salesianostriana.dam.ecoshop.controller;

import org.springframework.stereotype.Controller;

import com.salesianostriana.dam.ecoshop.service.ProductService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@RequiredArgsConstructor 
public class ProductController {

	private final ProductService service;
}
