package com.salesianostriana.dam.ecoshop.controller;

import org.springframework.stereotype.Controller;

import com.salesianostriana.dam.ecoshop.service.OrderLineService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@RequiredArgsConstructor
public class OrderLineController {

	private final OrderLineService service;
}
