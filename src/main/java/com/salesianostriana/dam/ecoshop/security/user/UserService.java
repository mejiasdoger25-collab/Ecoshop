package com.salesianostriana.dam.ecoshop.security.user;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @PostConstruct
    public void initUsers() {

        if (userRepository.count() == 0) {

            userRepository.save(
                    User.builder()
                            .username("admin")
                            .password("{noop}admin")
                            .role(Role.ADMIN)
                            .build()
            );

            userRepository.save(
                    User.builder()
                            .username("user")
                            .password("{noop}user")
                            .role(Role.USER)
                            .build()
            );

            userRepository.save(
                    User.builder()
                            .username("vip")
                            .password("{noop}vip")
                            .role(Role.VIP)
                            .build()
            );

        }

    }

}