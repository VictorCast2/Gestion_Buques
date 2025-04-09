package com.example.buques.service;

import com.example.buques.docs.Usuario.Usuario;
import com.example.buques.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("el usuario " + correo + " no existe."));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(usuario.getRol().name())));

        return new User(
                usuario.getCorreo(),
                usuario.getPassword(),
                usuario.isEnabled(),
                usuario.isAccountNoExpired(),
                usuario.isCredentialNoExpired(),
                usuario.isAccountNoLocked(),
                authorityList
        );
    }
}