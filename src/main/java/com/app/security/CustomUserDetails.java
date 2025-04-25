package com.app.security;

import com.app.collections.Usuario.Usuario;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {

    // Atributos para la autenticación y autorización del usuario
    private String correo;
    private String password;
    private List<GrantedAuthority> authorities = new ArrayList<>();
    private boolean isEnabled;
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private boolean credentialNoExpired;

    // Atributos adicionales de la clase Usuario
    private String nombres;
    private String apellidos;

    public CustomUserDetails(Usuario usuario) {
        this.correo = usuario.getCorreo();
        this.password = usuario.getPassword();
        this.getAuthorities().add(new SimpleGrantedAuthority("ROLE_".concat(usuario.getRol().name())));
        this.isEnabled = usuario.isEnabled();
        this.accountNoExpired = usuario.isAccountNoExpired();
        this.accountNoLocked = usuario.isAccountNoLocked();
        this.credentialNoExpired = usuario.isCredentialNoExpired();
        this.nombres = usuario.getNombres();
        this.apellidos = usuario.getApellidos();
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNoExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNoLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNoExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}