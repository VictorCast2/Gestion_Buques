package com.example.buques.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.buques.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collection;

/**
 * Filtro que valida el token JWT en cada petición.
 * Se encarga de extraer el token del header de la petición y validar su autenticidad.
 * Si el token es válido, se establece la autenticación en el contexto de seguridad de Spring.
 */
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    // Instancia de JwtUtils para validar el token JWT
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // obtención del token (recodar que se envía en el header de la petición)
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken != null) { // Bearer mkdmsvjskdcmdjnskdcdknfhvgcfhyghj
            jwtToken = jwtToken.substring(7);
            DecodedJWT decodedJWT = jwtUtils.validarToken(jwtToken);
            String username = jwtUtils.extraerUsuario(decodedJWT);
            String stringAutorizaciones = jwtUtils.getClaimByName(decodedJWT, "authorities").asString();
            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAutorizaciones);
            // seteamos al usuario al contexto de Spring Security
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }
        filterChain.doFilter(request, response);
    }
}