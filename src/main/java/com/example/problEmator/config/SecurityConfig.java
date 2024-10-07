package com.example.problEmator.config;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/form", "/submit", "/reports", "/css/**", "/js/**", "/images/**").permitAll() // Ścieżki publiczne
                        .requestMatchers("/h2-console/**").authenticated() // Konsola H2 dostępna tylko po zalogowaniu
                        .requestMatchers("/reports/edit/**", "/reports/update/**").authenticated() // Edycja tylko po zalogowaniu
                        .anyRequest().authenticated() // Inne ścieżki wymagają logowania
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll()
                )
                // Wyłączamy CSRF tylko dla H2, ale nie dla reszty aplikacji
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                // Pozwalamy na wyświetlanie ramek tylko dla H2
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Zezwalamy na wyświetlanie ramek z tej samej domeny
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}