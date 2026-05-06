package com.salesianostriana.dam.ecoshop.controller;

import org.springframework.stereotype.Controller;

import com.salesianostriana.dam.ecoshop.service.ProductService;

import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.NoArgsConstructor;

@Controller
@Data
@AllArgsConstructor 
public class ProductController {

	private final ProductService service;
}
