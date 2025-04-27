package com.app.config;

import com.app.config.filter.JwtTokenValidatorFilter;
import com.app.repository.UsuarioRepository;
import com.app.service.UserDetailServiceImpl;
import com.app.utils.JwtUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                // habilitamos la protección CSRF usando cookies
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // asi el tiempo de expiración de la session dependerá del tiempo de expiración del token
                )
                .authorizeHttpRequests(auth -> {
                    // Configurar endpoints públicos estáticos (sin autenticación)
                    auth.requestMatchers("/", "/css/**", "/js/**").permitAll();

                    // Configurar endpoints públicos (sin autenticación)
                    auth.requestMatchers(HttpMethod.GET, "/auth/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/test/hello").permitAll();

                    // Configurar endpoints privados
                    auth.requestMatchers(HttpMethod.GET, "/test/hello-protegido").authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/test/admin").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.GET, "/test/inspector").hasRole("INSPECTOR");

                    // Configurar endpoints NO ESPECIFICADOS
                    // auth.anyRequest().denyAll();
                    auth.anyRequest().permitAll(); // cambiar luego
                })

                // Configuración de logout
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout") // redirección después de cerrar sesión
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