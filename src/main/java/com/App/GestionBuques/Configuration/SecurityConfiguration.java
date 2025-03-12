package com.App.GestionBuques.Configuration;

import com.App.GestionBuques.AgenteNav.Repository.AgenteNavRepository;
import com.App.GestionBuques.Configuration.Services.CustomUserDetailsServices;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    /**
     * Configura las reglas de seguridad de la aplicación.
     * @param http La configuración de seguridad HTTP.
     * @return El filtro de seguridad configurado.
     * @throws Exception Si ocurre un error al configurar la seguridad.
     */
    @Bean
    public SecurityFilterChain securedFilterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilita CSRF
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/Api/Auth/Login", "/Api/Auth/logout").permitAll() // Permite acceso público
                        .requestMatchers("/Api/**").authenticated() // Autenticación para otras rutas
                        .requestMatchers("/css/**", "/css/Error/**", "/img/**", "/js/**", "/assets/**").permitAll() // Permite acceso público
                        .requestMatchers("/Error/**", "/Error").permitAll() // Permite acceso público a la página de error
                        .requestMatchers("/Api/Home").permitAll()
                        .anyRequest().authenticated() // Autenticación para otras rutas
                )
                .formLogin(form -> form
                        .loginPage("/Api/Home") // Página de inicio de sesión personalizada
                        .loginProcessingUrl("/Api/Auth/Login") // URL de procesamiento de inicio de sesión
                        .usernameParameter("email") // Parámetro de nombre de usuario
                        .passwordParameter("password") // Parámetro de contraseña
                        .failureUrl("/Error")
                        .successHandler(AllSuccessHandler()) // Manejador de éxito personalizado
                        .permitAll() // Permitir acceso a la página de login
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/Error/403") // Página de acceso denegado
                )
                .logout(logout -> logout
                        .logoutUrl("/Api/Auth/logout") // URL de cierre de sesión
                        .logoutSuccessUrl("/Api/Auth/Login?Logout") // Redirige tras cerrar sesión
                        .invalidateHttpSession(true) // Invalida completamente la sesión
                        .clearAuthentication(true) // Borra la autenticación actual
                        .deleteCookies("JSESSIONID") // Borra la cookie de sesión
                        .permitAll() // Permitir acceso a la página de logout
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Crea nueva sesión al autenticarse
                        .invalidSessionUrl("/Api/Auth/login") // Redirige si la sesión es inválida
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession) // Nueva sesión tras autenticarse
                )
                .httpBasic(Customizer.withDefaults()); // Habilita autenticación básica
        return http.build();
    }

    /**
     * Configura el encriptador de contraseñas.
     * @return El encriptador de contraseñas personalizado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el servicio de usuarios.
     * @param usuarioRepository El repositorio de usuarios.
     * @return El servicio de usuarios personalizado.
     */
    @Bean
    public UserDetailsService userDetailsService(AgenteNavRepository usuarioRepository) {
        return new CustomUserDetailsServices(usuarioRepository);
    }

    /**
     * Configura el proveedor de autenticación.
     * @param userDetailsService El servicio de usuarios.
     * @param passwordEncoder El encriptador de contraseñas.
     * @return El proveedor de autenticación personalizado.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    /**
     * Configura el manejador de autenticación.
     * @param http La configuración de seguridad HTTP.
     * @param userDetailsService El servicio de usuarios.
     * @param passwordEncoder El encriptador de contraseñas.
     * @return El manejador de autenticación personalizado.
     * @throws Exception Si ocurre un error al configurar el manejador de autenticación.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider(userDetailsService, passwordEncoder))
                .build();
    }

    /**
     * Configura el manejador de éxito de autenticación.
     * @return El manejador de éxito de autenticación personalizado.
     */
    @Bean
    public AuthenticationSuccessHandler AllSuccessHandler() {
        return (request, response, authentication) -> {
            String role = authentication.getAuthorities().stream()
                    .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                    .findFirst()
                    .orElse("");
            String redirectUrl = switch (role) {
                case "ADMIN" -> "Api/Admin/Home";
                case "OPERADOR_PORTUARIO" -> "Api/OperadorPortuario/Home";
                case "INSPECTOR" -> "Api/Inspector/Home";
                case "AGENTE_NAVIERO" -> "Api/AgenteNaviero/Home";
                default -> "/Api/Auth/Login";
            };
            response.sendRedirect(redirectUrl);
        };
    }

}