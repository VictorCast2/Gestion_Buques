package com.app.configuration.filter;

import com.app.collections.Usuario.Usuario;
import com.app.repository.UsuarioRepository;
import com.app.utils.CustomUserDetails;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.app.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    private UsuarioRepository usuarioRepository;

    public JwtTokenValidatorFilter(JwtUtils jwtUtils, UsuarioRepository usuarioRepository) {
        this.jwtUtils = jwtUtils;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        String jwtToken = null;

        // recorremos las cookies para obtener la que tiene el token
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                // "access_token" este es el nombre de la cookie que tiene el token
                if ("access_token".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        if (jwtToken != null) {

            DecodedJWT decodedJWT = jwtUtils.validarToken(jwtToken);

            String username = jwtUtils.extraerUsuario(decodedJWT); // se extrae el email del usuario
            String stringAutorizaciones = jwtUtils.getClaimByName(decodedJWT, "authorities").asString();

            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAutorizaciones);

            Usuario usuario = usuarioRepository.findByCorreo(username)
                    .orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado en el filtro"));

            CustomUserDetails customUserDetails = new CustomUserDetails(usuario);

            // seteamos al usuario al contexto de Spring Security
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, authorities);
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }

        filterChain.doFilter(request, response);
    }
}