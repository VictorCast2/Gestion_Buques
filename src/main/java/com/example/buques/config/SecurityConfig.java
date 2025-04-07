package com.example.buques.config;

import com.example.buques.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(Customizer.withDefaults()) // habilitamos csrf por defecto
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // asi el tiempo de expiración de la session dependerá del tiempo de expiración del token
                )
                .authorizeHttpRequests(auth -> {

                    // Configurar endpoints públicos
                    auth.requestMatchers(HttpMethod.GET, "/auth/hello").permitAll();

                    // Configurar endpoints privados
                    auth.requestMatchers(HttpMethod.GET, "/auth/hello-protegido").hasAnyRole("ADMIN", "INSPECTOR");
                    auth.requestMatchers(HttpMethod.GET, "/auth/admin").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/auth/inspector").hasRole("INSPECTOR");

                    // Configurar endpoints NO ESPECIFICADOS
                    // auth.anyRequest().denyAll();
                    auth.anyRequest().permitAll(); // cambiar luego
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
