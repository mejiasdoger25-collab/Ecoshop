package com.salesianostriana.dam.ecoshop.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianostriana.dam.ecoshop.security.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	
	
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    
    
    @GetMapping("/register")
    public String showRegisterForm() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String name, @RequestParam String email,
    						@RequestParam String phone, Model model, HttpServletRequest request) {
        
    	try {

    		 var user = userService.registerUser(
    	                username,
    	                password,
    	                name,
    	                email,
    	                phone
    	        );

    	        Authentication auth = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());

    	        SecurityContextHolder.getContext().setAuthentication(auth);

    	        HttpSession session = request.getSession(true);

    	        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

    	        return "redirect:/";//new acc + sign in = /home page (products list)
    		
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
        
    }

}