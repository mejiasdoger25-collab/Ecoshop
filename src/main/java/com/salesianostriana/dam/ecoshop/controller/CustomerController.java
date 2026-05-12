package com.salesianostriana.dam.ecoshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.ecoshop.service.CustomerService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Controller
@Data //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@RequiredArgsConstructor
public class CustomerController {

	private final CustomerService service;
	
	@GetMapping({"/login"})
    public String login() {
        return "login5"; 
    }
	
	@PostMapping("/login/submit")
	public String processForm (@ModelAttribute ("customer") Model model) {
		
		return "redirect:/home";
	}
	
}
