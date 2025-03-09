package com.App.Gestion_Buques.Services;

import com.App.Gestion_Buques.Usuario.Repository.Entity.UsuarioEntity;
import com.App.Gestion_Buques.Usuario.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import lombok.*;

@Data
@Service
@AllArgsConstructor
public class CustomUserDetailsServices implements UserDetailsService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    /**
     * Método que se encarga de cargar un usuario por su nombre de usuario
     * @param username Nombre de usuario
     * @return Devuelve un usuario con los datos del usuario encontrado
     * @throws UsernameNotFoundException Excepción que se lanza si no se encuentra el usuario
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Intentando autenticar usuario: " + username);
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("Usuario no encontrado en la base de datos.");
                    return new UsernameNotFoundException("Usuario no encontrado ... ");
                });
        System.out.println("Usuario encontrado : " + usuario.getUsername() + " ... " );
        System.out.println(" La contraseña es : " + usuario.getPassword() +  " ... " );
        System.out.println(" El email es : " + usuario.getEmail() +  " ... " );
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