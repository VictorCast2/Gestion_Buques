package com.App.Gestion_Buques.Usuario.Entity;

import com.App.Gestion_Buques.Empresa.Entity.EmpresaEntity;
import lombok.*;
import java.util.*;
import jakarta.persistence.*;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    // Cardinalidad para la tabla roles
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Roles", joinColumns = @JoinColumn(name = "usuario_Id"))
    @Column(name = "Rol", nullable = false)
    private Set<String> Roles;

    // Cardinalidad para la tabla empresas
    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "Id",
                foreignKey = @ForeignKey(name = "FK_usuario-empresa"))
    private EmpresaEntity empresa;

    /**
     * Obtiene los roles del usuario.
     * @return Los roles del usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Roles.stream()
                .map(SimpleGrantedAuthority::new) // Convierte cada rol en una autoridad
                .collect(Collectors.toList());
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return La contraseña del usuario.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta del usuario está bloqueada.
     * @return Si la cuenta del usuario está bloqueada.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales del usuario han expirado.
     * @return Si las credenciales del usuario han expirado.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica si el usuario está habilitado.
     * @return Si el usuario está habilitado.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Obtiene el nombre de usuario.
     * @return El nombre de usuario.
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return La contraseña del usuario.
     */
    @Override
    public String getPassword() {
        return this.password;
    }

}