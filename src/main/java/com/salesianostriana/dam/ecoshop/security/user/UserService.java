package com.salesianostriana.dam.ecoshop.security.user;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.ecoshop.model.Customer;
import com.salesianostriana.dam.ecoshop.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	//método para registrar nuevos usuarios
	// Añadido al final de la clase UserService
	public User registerUser(String username, String password, String name, String email, String phone) {
	    
	    if (userRepository.findByUsername(username).isPresent()) {
	        throw new IllegalArgumentException("The username is already in use");
	    }

	    User user = User.builder()
	            .username(username)
	            .password(passwordEncoder.encode(password))
	            .role(Role.USER)
	            .build();

	    user = userRepository.save(user);

	    Customer customer = Customer.builder()
	            .name(name)
	            .email(email)
	            .phone(phone)
	            .registrationDate(LocalDateTime.now())
	            .totalSpent(0.0)
	            .vip(false)
	            .balance(0.0)
	            .user(user)
	            .build();

	    customerRepository.save(customer);
	    return user;
	}
	
	

}