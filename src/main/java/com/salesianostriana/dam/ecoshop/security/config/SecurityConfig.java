package com.salesianostriana.dam.ecoshop.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                // PUBLIC
                .requestMatchers(
                    "/",
                    "/products/list",
                    "/css/**",
                    "/javascript/**",
                    "/images/**"//acceso cambiado para el login background
                ).permitAll()

                // ADMIN
                .requestMatchers(
                    "/products/new/**",
                    "/products/edit/**",
                    "/products/delete/**",
                    "/customers/**"
                ).hasRole("ADMIN")

                // USER
                .requestMatchers(
                    "/orders/**"
                ).hasAnyRole("USER", "USER_VIP", "ADMIN")

                // everything else
                .anyRequest().authenticated()

            )

            .formLogin(form -> form

                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()

            )

            .logout(logout -> logout.permitAll());

        return http.build();

    }


    @Bean
    UserDetailsService userDetailsService() {

    UserDetails admin =
            User.withUsername("admin")
                    .password("{noop}admin")
                    .roles("ADMIN")
                    .build();

    UserDetails user =
            User.withUsername("user")
                    .password("{noop}1234")
                    .roles("USER")
                    .build();

    UserDetails vip =
            User.withUsername("vip")
                    .password("{noop}1234")
                    .roles("USER_VIP")
                    .build();

    return new InMemoryUserDetailsManager(
            admin,
            user,
            vip
    );

}

}