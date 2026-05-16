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
