package com.App.GestionBuques.Configuration.Services;

import com.App.GestionBuques.AgenteNav.Model.AgenteNavEntity;
import com.App.GestionBuques.AgenteNav.Repository.AgenteNavRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Data
@Service
@AllArgsConstructor
public class CustomUserDetailsServices implements UserDetailsService {

    @Autowired
    private final AgenteNavRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Intentando autenticar usuario: " + email);
        AgenteNavEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("Usuario no encontrado en la base de datos.");
                    return new UsernameNotFoundException("Usuario no encontrado ... ");
                });
        System.out.println(" El email es : " + usuario.getUsername() +  " ... " );
        System.out.println(" La contraseña es : " + usuario.getPassword() +  " ... " );
        return new User(
                // Devuelve un nuevo usuario con los datos del usuario encontrado
                usuario.getUsername(),
                // Devuelve la contraseña del usuario
                usuario.getPassword(),
                // Devuelve si el usuario está habilitado
                usuario.isEnabled(),
                // Devuelve si la cuenta del usuario ha expirado
                usuario.isAccountNonExpired(),
                // Devuelve si las credenciales del usuario han expirado
                usuario.isCredentialsNonExpired(),
                // Devuelve si la cuenta del usuario está bloqueada
                usuario.isAccountNonLocked(),
                // Devuelve las autoridades del usuario
                usuario.getAuthorities()
        );
    }

}