package com.example.buques.utils;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String  llavePrivada;

    @Value("${security.jwt.user.generator}")
    private String origenToken;

    public String crearToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(llavePrivada);

        String usuario = authentication.getPrincipal().toString(); // obtenemos al usuario
        String permisos = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        return null;
    }

}
