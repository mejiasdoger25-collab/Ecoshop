package com.salesianostriana.dam.ecoshop.security.user;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;


    @PostConstruct
    public void initUsers() {

        if (userRepository.count() == 0) {

            userRepository.save(
                    User.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("admin"))
                            .role(Role.ADMIN)
                            .build()
            );

            userRepository.save(
                    User.builder()
                            .username("user")
                            .password(passwordEncoder.encode("user"))
                            .role(Role.USER)
                            .build()
            );

            userRepository.save(
                    User.builder()
                            .username("vip")
                            .password(passwordEncoder.encode("vip"))
                            .role(Role.VIP)
                            .build()
            );

        }

    }

}