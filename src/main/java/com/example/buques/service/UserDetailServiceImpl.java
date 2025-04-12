package com.example.buques.service;

import com.example.buques.docs.Usuario.Enum.EIdentificacion;
import com.example.buques.docs.Usuario.Enum.ERol;
import com.example.buques.docs.Usuario.Usuario;
import com.example.buques.dto.request.AuthLoginRequest;
import com.example.buques.dto.response.AuthResponse;
import com.example.buques.repository.UsuarioRepository;
import com.example.buques.utils.JwtUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Data
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario: " + correo + " no existe."));
        return new User(
                usuario.getCorreo(),
                usuario.getPassword(),
                usuario.isEnabled(),
                usuario.isAccountNoExpired(),
                usuario.isCredentialNoExpired(),
                usuario.isAccountNoLocked(),
                usuario.getAuthorities()
        );
    }

    /**
     * Método para loguear al usuario
     * @param authLoginRequest parámetro con los datos necesarios para el logueo del usuario (username, password)
     * @return un objeto de tipo authResponse que contiene el correo del usuario, un mensaje de satisfacción, el token de acceso y el estado
     */
    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {

        // obtenemos el correo y la contraseña del usuario
        String correo = authLoginRequest.correo();
        String password = authLoginRequest.password();

        // verificamos que el correo y la contraseña no sean nulos
        Authentication authentication = this.authentication(correo, password); // este método se llama abajo, es el que autentica al usuario
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // obtenemos el usuario logueado
        String tokenDeAcceso = jwtUtils.crearToken(authentication);

        // obtenemos los permisos del usuario
        AuthResponse authResponse;
        authResponse = new AuthResponse(correo, "Usuario Logueado Exitosamente", tokenDeAcceso, true);
        return authResponse;
    }

    /**
     * Método para autenticar a un usuario
     * @param correo que el usuario setteo en el input de correo
     * @param password contraseña que el usuario setteo en el input de password
     * @return un objeto de tipo UsernamePasswordAuthenticationToken con los datos del usuario como correo, password y permisos (authorities)
     */
    public Authentication authentication(String correo, String password) {
        UserDetails userDetails = this.loadUserByUsername(correo);

        // si el usuario es nulo, significa que no existe en la base de datos, asi que mandamos un error
        if (userDetails == null) {
            throw new BadCredentialsException("Correo o contraseña incorrectos");
        }

        //Comparamos las contraseñas
        if (!encoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }

        // si el usuario existe, creamos un objeto de tipo UsernamePasswordAuthenticationToken con los datos del usuario
        return new UsernamePasswordAuthenticationToken(correo, userDetails.getPassword(), userDetails.getAuthorities());
    }
    /**
     * Método para registrar un nuevo usuario
     * @param usuario objeto de tipo Usuario con los datos del nuevo usuario
     * @return un objeto de tipo AuthResponse que contiene el correo del usuario, un mensaje de satisfacción, el token de acceso y el estado
     */
    public AuthResponse registerUser(Usuario usuario, String tipoIdentificacion) {
        // Verificar si el correo ya existe
        Optional<Usuario> existente = usuarioRepository.findByCorreo(usuario.getCorreo());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado.");
        }

        switch (tipoIdentificacion) {
            case "CC" -> usuario.setTipoIdentificacion(EIdentificacion.CC);
            case "CE" -> usuario.setTipoIdentificacion(EIdentificacion.CE);
            case "DE" -> usuario.setTipoIdentificacion(EIdentificacion.DE);
            case "NIT" -> usuario.setTipoIdentificacion(EIdentificacion.NIT);
            case "PP" -> usuario.setTipoIdentificacion(EIdentificacion.PP);
            case "RUT" -> usuario.setTipoIdentificacion(EIdentificacion.RUT);
            case "TE" -> usuario.setTipoIdentificacion(EIdentificacion.TE);
            case "TI" -> usuario.setTipoIdentificacion(EIdentificacion.TI);
            default -> throw new IllegalArgumentException("Tipo de identificación no válido: " + tipoIdentificacion);
        }

        // Crear el nuevo usuario
        Usuario nuevoUsuario = Usuario.builder()
                .correo(usuario.getCorreo())
                .password(encoder.encode(usuario.getPassword()))
                .rol(ERol.INVITADO)
                .tipoIdentificacion(usuario.getTipoIdentificacion())
                .numeroIdentificacion(usuario.getNumeroIdentificacion())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .telefono(usuario.getTelefono())
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .build();
        usuarioRepository.save(nuevoUsuario);

        // Autenticar manualmente para generar el token
        Authentication authentication = this.authentication(usuario.getCorreo(), usuario.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar el token
        String jwt = jwtUtils.crearToken(authentication);

        return new AuthResponse(nuevoUsuario.getCorreo(), "Usuario registrado exitosamente", jwt, true);
    }

}