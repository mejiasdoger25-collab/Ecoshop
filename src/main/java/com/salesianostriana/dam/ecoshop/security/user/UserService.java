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

	/*
	@PostConstruct
	public void initUsers() {

	    if (userRepository.count() == 0) {

	        User admin = userRepository.save(
	                User.builder()
	                        .username("admin")
	                        .password(passwordEncoder.encode("admin"))
	                        .role(Role.ADMIN)
	                        .build()
	        );

	        User user = userRepository.save(
	                User.builder()
	                        .username("user")
	                        .password(passwordEncoder.encode("user"))
	                        .role(Role.USER)
	                        .build()
	        );

	        User vip = userRepository.save(
	                User.builder()
	                        .username("vip")
	                        .password(passwordEncoder.encode("vip"))
	                        .role(Role.VIP)
	                        .build()
	        );

	        customerRepository.save(
	                Customer.builder()
	                        .name("Admin System")
	                        .email("admin@ecoshop.com")
	                        .phone("111111111")
	                        .registrationDate(LocalDateTime.now())
	                        .totalSpent(0)
	                        .vip(false)
	                        .balance(0)
	                        .user(admin)
	                        .build()
	        );

	        customerRepository.save(
	                Customer.builder()
	                        .name("User Normal")
	                        .email("user@ecoshop.com")
	                        .phone("222222222")
	                        .registrationDate(LocalDateTime.now())
	                        .totalSpent(0)
	                        .vip(false)
	                        .balance(0)
	                        .user(user)
	                        .build()
	        );

	        customerRepository.save(
	                Customer.builder()
	                        .name("VIP Premium")
	                        .email("vip@ecoshop.com")
	                        .phone("333333333")
	                        .registrationDate(LocalDateTime.now())
	                        .totalSpent(0)
	                        .vip(true)
	                        .balance(0)
	                        .user(vip)
	                        .build()
	        );

	    }

	}
	*/
	
	
	//el method de arriba no debe ir aquí, sino que se debe hacer a través de un import.sql o desde un dataseed
	
	
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