package com.app.configuration.security;

import com.app.configuration.filter.JwtTokenValidatorFilter;
import com.app.repository.UsuarioRepository;
import com.app.service.UserDetailServiceImpl;
import com.app.utils.JwtUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // deshabilitamos csrf por incompatibilidades con jwt
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // asi el tiempo de expiración de la session dependerá del tiempo de expiración del token (recordar que jwt se basa en una política sin sesiones)
                )
                .authorizeHttpRequests(auth -> {
                    // Configurar endpoints públicos (sin autenticación)
                    // autenticación
                    auth.requestMatchers(HttpMethod.GET, "/auth/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    // test
                    auth.requestMatchers(HttpMethod.GET, "/test/hello").permitAll();

                    // Configurar endpoints privados
                    // test
                    auth.requestMatchers(HttpMethod.GET, "/test/hello-protegido").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/test/admin").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/test/inspector").hasRole("INSPECTOR");
                    // perfil
                    auth.requestMatchers(HttpMethod.GET, "/buques/perfil/").authenticated();
                    auth.requestMatchers(HttpMethod.POST, "/buques/perfil/vincular-empresa").hasAnyRole("AGENTE_NAVIERO", "INVITADO");
                    auth.requestMatchers(HttpMethod.POST, "/buques/perfil/**").authenticated();

                    /* ----- Admin ----- */
                    auth.requestMatchers(HttpMethod.GET, "/buques/admin/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/buques/dashboard-admin/**").hasRole("ADMIN");
                    // muelle
                    auth.requestMatchers(HttpMethod.GET, "/buques/registro-muelle/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/buques/registro-muelle/**").hasRole("ADMIN");
                    // horarios
                    auth.requestMatchers(HttpMethod.GET, "/buques/horarios/**").hasRole("ADMIN");
                    // solicitudes
                    auth.requestMatchers(HttpMethod.GET, "/buques/gestion-solicitud/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/buques/gestion-solicitud/**").hasRole("ADMIN");
                    // registro de usuarios
                    auth.requestMatchers(HttpMethod.GET, "/buques/registro-usuarios/**").hasRole("ADMIN");

                    /* ----- Agente Naviero ----- */
                    // atraques
                    auth.requestMatchers(HttpMethod.GET, "/buques/SolicitudAtraque/**").hasRole("AGENTE_NAVIERO");
                    auth.requestMatchers(HttpMethod.POST, "/buques/SolicitudAtraque/**").hasRole("AGENTE_NAVIERO");
                    // procesos
                    auth.requestMatchers(HttpMethod.GET, "/buques/Procesos/**").hasRole("AGENTE_NAVIERO");
                    auth.requestMatchers(HttpMethod.POST, "/buques/Procesos/**").hasRole("AGENTE_NAVIERO");

                    // Configurar endpoints públicos estáticos (sin autenticación)
                    auth.requestMatchers("/", "/css/**", "/js/**", "/**").permitAll();

                    // Configurar endpoints NO ESPECIFICADOS
                    auth.anyRequest().denyAll();
                    // auth.anyRequest().permitAll();
                })

                // Configuración de errores
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/error/403")
                )

                // Configuración de logout
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/buques/?logout") // redirección después de cerrar sesión (falta configurar el parámetro ?logout)
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "access_token")
                )

                // Añadimos el filtro que creamos y lo ejecutamos antes del filtro de BasicAuthenticationFilter (este es el encargado de verificar si estamos autorizados)
                .addFilterBefore(new JwtTokenValidatorFilter(jwtUtils, usuarioRepository), BasicAuthenticationFilter.class)
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