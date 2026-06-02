package com.salesianostriana.dam.ecoshop.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        
        /*
         * <!--cambiado para que Cross Site Request Forgery no pase por thymeleaf con th:action. así no crea la session late y crashea el render-->
         * 
         * <!--hecho con if porque el token no existe en todas las request, solo en algunas-->
         * 
         * <!--es por esto que lo he hecho con un enlace en lugar de formulario, para evitar todo el tema de los tokens-->
         * */
        	.csrf(csrf -> csrf.disable())
        
            .authorizeHttpRequests(auth -> auth

                // PUBLIC
                .requestMatchers(
                    "/",
                    "/products/list",
                    "/products/details", 
                    "/products/details/{id}",
                    "/error",
                    "/fragments/navbar",
                    "/fragments/footer",
                    "/css/**",
                    "/javascript/**",
                    "/images/**",//acceso cambiado para el login background
                    "/products/category/{id}",
                    "/products/eco/**",
                    "/register"
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
                ).hasAnyRole("USER", "VIP", "ADMIN")

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

