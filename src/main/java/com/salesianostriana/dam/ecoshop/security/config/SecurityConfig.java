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
                    "/img/**"
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