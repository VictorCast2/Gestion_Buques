package com.example.buques.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.*;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.key.private}")
    private String  llavePrivada;

    @Value("${security.jwt.user.generator}")
    private String origenToken;

    /**
     * Método para la creación de un token
     * @param authentication parámetro para la obtención de los usuarios y sus permisos
     * @return jwtToken (codificado)
     */
    public String crearToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(llavePrivada); // algoritmo de firma del token
        String usuario = authentication.getPrincipal().toString(); // obtenemos al usuario de la sesión
        // obtenemos los permisos del usuario
        String permisos = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority) // convertimos los permisos a un String
                .collect(Collectors.joining(",")); // obtenemos los permisos del usuario y los convertimos a un String separado por comas
        String jwtToken = JWT.create()
                .withIssuer(origenToken) // usuario que generara el token
                .withSubject(usuario) // sujeto al que se le genera el token
                .withClaim("authorities", permisos) // generación del claim con los permisos del usuario
                .withIssuedAt(new Date()) // fecha de creación del token
                .withExpiresAt(new Date(System.currentTimeMillis() + 7200000)) // fecha de vigencia del token (hora de generación actual en milisegundos + los milisegundos para la expiración {2hrs})
                .withJWTId(UUID.randomUUID().toString()) // generación del id del token
                .withNotBefore(new Date()) // especifica el momento en el que el token se considera válido (en este caso, desde su generación)
                .sign(algorithm); // firma del token

        return jwtToken;
    }

    /**
     * Método para decodificar y validar el token
     * @param token parámetro para la obtención del token de usuario
     * @return el token decodificado o una excepción si el token es inválido
     */
    public DecodedJWT validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(llavePrivada);
            JWTVerifier verifier = JWT.require(algorithm) // algoritmo de firma del token
                    .withIssuer(origenToken) // usuario que genero el token (en este caso el backend)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token); // verificamos el token
            return decodedJWT;
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token Invalido, NO Autorizado");
        }
    }

    /**
     * Método para obtener el usuario que viene del token
     * @param decodedJWT parámetro para la obtención del token decodificado (incluye al sujeto)
     * @return el correo del usuario que genero el token
     */
    public String extraerUsuario(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject(); // obtenemos el sujeto del token
    }

    /**
     * Método para obtener un Claim por su correo del token
     * @param decodedJWT parámetro para la obtención del token decodificado (incluye el claim)
     * @param claimName parámetro con el correo del Claim a obtener
     * @return el Claim solicitado
     */
    public Claim getClaimByName(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName); // obtenemos el claim por su nombre
    }

    /**
     * Método para obtener todos los Claims
     * @param decodedJWT parámetro para la obtención del token decodificado (incluye los claims)
     * @return un mapa con todos los Claims del token
     */
    public Map<String, Claim> getClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims(); // obtenemos todos los claims del token
    }

}