package com.salesianostriana.dam.ecoshop.controller;

import org.springframework.stereotype.Controller;

import com.salesianostriana.dam.ecoshop.service.OrderService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Controller
@Data
@AllArgsConstructor
public class OrderController {

	private final OrderService service;
}
