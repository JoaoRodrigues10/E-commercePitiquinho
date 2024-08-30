package br.loja.pitiquinho.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.loja.pitiquinho.service.UsuarioDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/h2-console/**").permitAll() // Permitir acesso ao console H2
                    .anyRequest().permitAll() // Permitir acesso irrestrito a todas as rotas
            )
            .headers(headers -> headers.frameOptions().disable()); // Necessário para o console H2 funcionar corretamente
    
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UsuarioDetailsService();
    }
}


/*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                    .requestMatchers("/registro").permitAll() // Permite acesso à página de registro
                    .requestMatchers("/login").permitAll() // Permite acesso à página de login
                    .requestMatchers("/console/**").permitAll() // Permite acesso ao console
                    .anyRequest().authenticated()
            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login")
                    .permitAll()
            )
            .logout(logout ->
                logout.permitAll()
            );
        return http.build();
    }
*/