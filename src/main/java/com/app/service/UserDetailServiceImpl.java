package com.app.service;

import com.app.collections.Usuario.Enum.EIdentificacion;
import com.app.collections.Usuario.Enum.ERol;
import com.app.collections.Usuario.Usuario;
import com.app.dto.request.AuthCreateUserRequest;
import com.app.dto.request.AuthLoginRequest;
import com.app.dto.response.AuthResponse;
import com.app.repository.UsuarioRepository;
import com.app.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("el correo " + correo + " no existe."));

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

    /**
     * Método para la creación de un usuario
     * @param authCreateUserRequest parámetro con los datos básicos del usuario
     * @return un objeto de tipo authResponse que contiene el correo del usuario, un mensaje de satisfacción, el token de acceso y el estado
     * @nota: el usuario creado tendrá por defecto el rol de Invitado y lo referente a los datos de su empresa ó inspecciones,
     * serán nulos, por ser la primera vez que se crea al usuario
     */
    public AuthResponse createUser(@Valid AuthCreateUserRequest authCreateUserRequest) {
        EIdentificacion tipoIdentificacion = EIdentificacion.valueOf(authCreateUserRequest.tipoIdentificacion());
        String numeroIdentificacion = authCreateUserRequest.numeroIdentificacion();
        String nombres = authCreateUserRequest.nombres();
        String apellidos = authCreateUserRequest.apellidos();
        String telefono = authCreateUserRequest.telefono();
        String correo = authCreateUserRequest.correo();
        String password = authCreateUserRequest.password();

        // creamos al usuario con los datos del dto y valores por defecto
        Usuario usuario = Usuario.builder()
                .tipoIdentificacion(tipoIdentificacion)
                .numeroIdentificacion(numeroIdentificacion)
                .nombres(nombres)
                .apellidos(apellidos)
                .telefono(telefono)
                .correo(correo)
                .password(encoder.encode(password))
                .rol(ERol.INVITADO)
                .empresa(null)
                .inspecciones(null)
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .build();

        Usuario usuarioCreado = usuarioRepository.save(usuario); // salvamos al usuario

        return new AuthResponse("usuario creado exitosamente");
    }


    /**
     * Método para loguear al usuario
     * @param authLoginRequest parámetro con los datos necesarios para el logueo del usuario (username, password)
     * @return el token codificado, el cual en el controller se le pasa a una cookie de session
     */
    public String loginUser(AuthLoginRequest authLoginRequest) {

        String correo = authLoginRequest.correo();
        String password = authLoginRequest.password();

        try {
            Authentication authentication = this.authentication(correo, password); // este método se llama abajo, es el que autentica al usuario
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtUtils.crearToken(authentication);

        } catch (BadCredentialsException | UsernameNotFoundException exception) {
            throw exception;
        }

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
        return new UsernamePasswordAuthenticationToken(correo, userDetails.getPassword(), userDetails.getAuthorities());
    }

}
