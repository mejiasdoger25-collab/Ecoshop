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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
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

            .logout(logout -> logout
            	       .logoutUrl("/logout")
            	       .logoutSuccessUrl("/login?logout")
            	       .invalidateHttpSession(true)//sesión destruida
            	       .deleteCookies("JSESSIONID")//cookies borradas
            	       .permitAll()
            	);


        return http.build();

    }


    

}

