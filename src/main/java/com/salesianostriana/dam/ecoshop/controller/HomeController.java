package com.salesianostriana.dam.ecoshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@RequiredArgsConstructor
public class HomeController {

	@GetMapping("/")
    public String home() {
        return "redirect:/products/list";
	}
	
}

/*
de momento inútil dado que no aporta nada diff de /products/list, por ende, se podría implementar como mejora de tal forma que /home o / tenga algo distinto 
a lo que se aprecia en /products/list, como por ejemplo los más vendidos, últimos añadidos, ofertas, poco stock, etc ---->> lógica de negocio
*/