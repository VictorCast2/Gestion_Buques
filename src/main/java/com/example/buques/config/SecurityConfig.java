package com.example.buques.config;

import com.example.buques.config.filter.JwtTokenValidatorFilter;
import com.example.buques.service.UserDetailServiceImpl;
import com.example.buques.utils.JwtUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Data
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils; // inyectamos el JwtUtils para poder usarlo en el filtro

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // deshabilitamos csrf por problemas con el jwt, bloquea los endpoints (post, put, delete), investigar como trabajar con cookies si se desea habilitar
                .httpBasic(Customizer.withDefaults()) // habilitamos httpBasic (autenticación básica) por defecto
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // asi el tiempo de expiración de la session dependerá del tiempo de expiración del token
                )
                .authorizeHttpRequests(auth -> {
                    // Configurar endpoints públicos
                    auth.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/auth/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/test/hello").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/Error/**", "/Error").permitAll();

                    // Configurar endpoints públicos (static)
                    auth.requestMatchers("/Css/**", "/Js/**").permitAll();

                    // Configurar endpoints privados
                    auth.requestMatchers(HttpMethod.GET, "/test/hello-protegido").hasAnyRole("ADMIN", "INSPECTOR");
                    auth.requestMatchers(HttpMethod.GET, "/test/admin").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/test/inspector").hasRole("INSPECTOR");

                    // Configurar endpoints NO ESPECIFICADOS
                    // auth.anyRequest().denyAll();
                    auth.anyRequest().permitAll(); // cambiar luego
                })
                .formLogin(Customizer.withDefaults()) // habilitamos el login por defecto
                .logout(Customizer.withDefaults()) // habilitamos el logout por defecto
                // Añadimos el filtro que creamos y lo ejecutamos antes del filtro de BasicAuthenticationFilter (este es el encargado de verificar si estamos autorizados)
                .addFilterBefore(new JwtTokenValidatorFilter(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // devuelve el AuthenticationManager por defecto
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // AuthenticationProvider por defecto
        provider.setUserDetailsService(userDetailService); // inyectamos el UserDetailServiceImpl para poder usarlo en el provider
        provider.setPasswordEncoder(passwordEncoder()); // encriptador de contraseñas
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Enncriptador de contraseñas
    }

}