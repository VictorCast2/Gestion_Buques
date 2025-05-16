package com.app.service;

import com.app.collections.Usuario.pojo.Empresa;
import com.app.collections.Usuario.pojo.Redis.TwoFactorEnabledRequest;
import com.app.utils.CustomUserDetails;
import com.app.collections.Usuario.Enum.*;
import com.app.collections.Usuario.Usuario;
import com.app.dto.request.*;
import com.app.dto.response.AuthResponse;
import com.app.repository.UsuarioRepository;
import com.app.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    @Qualifier("twoFactorEnabledRequestRedisTemplate")
    private RedisTemplate<String, TwoFactorEnabledRequest> twoFactorRedisTemplate;

    @Autowired
    @Qualifier("authLoginRequestRedisTemplate")
    private RedisTemplate<String, AuthLoginRequest> authRedisTemplate;


    /**
     * Método para cargar al usuario por su correo
     * @param correo parámetro por el cual vamos a buscar al usuario, este campo es único
     * @return un objeto de tipo UserDetails con los datos del usuario
     */
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("el correo " + correo + " no existe."));
        return new CustomUserDetails(usuario);
    }

    /**
     * Método para buscar a un usuario en la base de datos
     * @param correo parámetro por el cual vamos a buscar al usuario, este campo es único
     * @return al modelo Usuario con todos sus datos (tener cuidado con los datos que se mostraran en las vistas)
     */
    public Usuario getUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("el correo " + correo + " no existe."));
    }

    /**
     * Método para obtener la lista de Agentes Navieros con una empresa asociada
     * @return una lista de usuarios con una empresa registrada
     */
    public List<Usuario> getAgentesNavieros() {
        return usuarioRepository.findByEmpresaNotNull().stream()
                .filter(u -> u.getEmpresa() != null && u.getEmpresa().getEstadoEmpresa().name().equals("PENDIENTE"))
                .toList();
    }

    /**
     * Método para la creación de un usuario
     * @param authCreateUserRequest parámetro con los datos básicos del usuario
     * @return un objeto de tipo authResponse que contiene un mensaje de satisfacción
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
                .imagen("user_default.jpg")
                .nombres(nombres)
                .apellidos(apellidos)
                .telefono(telefono)
                .correo(correo)
                .password(encoder.encode(password))
                .rol(ERol.AGENTE_NAVIERO)
                .empresa(null)
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .build();
        usuarioRepository.save(usuario); // salvamos al usuario

        // Guardamos el usuario en Redis
        if(!authRedisTemplate.hasKey("login:" + correo)) {
            AuthLoginRequest authLoginRequest = new AuthLoginRequest(correo, encoder.encode(password));
            authRedisTemplate.opsForValue().set("login:" + correo, authLoginRequest);
        }

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

            // Guardar contraseña encriptada en Redis
            AuthLoginRequest existingCache = authRedisTemplate.opsForValue().get("login:" + correo);
            if (existingCache == null) {
                AuthLoginRequest encryptedCache = new AuthLoginRequest(correo, encoder.encode(password));
                authRedisTemplate.opsForValue().set("login:" + correo, encryptedCache);
            }

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

        AuthLoginRequest cachedRequest = authRedisTemplate.opsForValue().get("login:" + correo);

        if (cachedRequest != null) {
            // Comparamos la contraseña usando BCrypt (u otro encoder)
            if (encoder.matches(password, cachedRequest.password())) {
                return new UsernamePasswordAuthenticationToken(
                        correo, userDetails.getPassword(), userDetails.getAuthorities());
            }
        }

        // si el usuario es nulo, significa que no existe en la base de datos, asi que mandamos un error
        if (userDetails == null) {
            throw new BadCredentialsException("Correo o contraseña incorrectos");
        }

        //Comparamos las contraseñas
        if (!encoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }

        // Guardamos en Redis la contraseña codificada (para futuras autenticaciones más rápidas)
        AuthLoginRequest newCache = new AuthLoginRequest(correo, userDetails.getPassword());
        authRedisTemplate.opsForValue().set("login:" + correo, newCache);

        return new UsernamePasswordAuthenticationToken(correo, userDetails.getPassword(), userDetails.getAuthorities());
    }

    /**
     * Método para actualizar contraseña al usuario
     * @param updatePasswordRequest parámetro con los datos necesarios para actualizar la contraseña (currentPassword, newPassword)
     * @param userDetails parámetro para extraer al usuario de la session
     * @return un objeto de tipo authResponse que contiene un mensaje de satisfacción
     */
    public AuthResponse updatePassword(@Valid UpdatePasswordRequest updatePasswordRequest, CustomUserDetails userDetails) {

        Usuario usuario = this.getUsuarioByCorreo(userDetails.getCorreo()); // usuario actual de la sesión
        String currentPassword = updatePasswordRequest.currentPassword();
        String newPassword = updatePasswordRequest.newPassword();

        // validamos que la contraseña sean iguales, sinó se manda el error
        if (!encoder.matches(currentPassword, usuario.getPassword())) {
            return new AuthResponse("Error: contraseña actual incorrecta");
        }

        // validamos que la nueva contraseña no sea igual a la anterior
        if (encoder.matches(newPassword, usuario.getPassword())) {
            return new AuthResponse("Error: la nueva contraseña no puede ser igual a la anterior");
        }

        // Actualizamos el valor en Redis
        AuthLoginRequest updatedCache = new AuthLoginRequest(usuario.getCorreo(), encoder.encode(newPassword));
        authRedisTemplate.opsForValue().set("login:" + usuario.getCorreo(), updatedCache);

        usuario.setPassword(encoder.encode(newPassword));
        usuarioRepository.save(usuario);
        return new AuthResponse("contraseña actualizada exitosamente");
    }

    /**
     * Método para actualizar los datos del usuario
     * @param updateUsuarioRequest parámetro con los datos necesarios para actualizar al usuario
     * @param customUserDetails parámetro para extraer al usuario de la session
     * @return un objeto de tipo authResponse que contiene un mensaje de satisfacción
     */
    public AuthResponse updateUsuario(@Valid UpdateUsuarioRequest updateUsuarioRequest, CustomUserDetails customUserDetails) {
        Usuario usuarioActualizado = this.getUsuarioByCorreo(customUserDetails.getCorreo()); // usuario actual de la sesión
        String correoAnterior = usuarioActualizado.getCorreo();

        // Le seteamos los nuevos datos al usuarioActualizado
        usuarioActualizado.setTipoIdentificacion(EIdentificacion.valueOf(updateUsuarioRequest.tipoIdentificacion()));
        usuarioActualizado.setNumeroIdentificacion(updateUsuarioRequest.numeroIdentificacion());
        usuarioActualizado.setNombres(updateUsuarioRequest.nombres());
        usuarioActualizado.setApellidos(updateUsuarioRequest.apellidos());
        usuarioActualizado.setTelefono(updateUsuarioRequest.telefono());
        usuarioActualizado.setCorreo(updateUsuarioRequest.correo());

        // validamos si el usuario tiene una empresa asignada para actualizar
        if (usuarioActualizado.getEmpresa() != null) {
            Empresa empresaActualizada = getEmpresa(updateUsuarioRequest, usuarioActualizado); // obtenemos los datos de la empresa
            usuarioActualizado.setEmpresa(empresaActualizada);
        }

        usuarioRepository.save(usuarioActualizado);

        // 1. Guardamos la contraseña actual desde Redis (si existe)
        AuthLoginRequest oldCache = authRedisTemplate.opsForValue().get("login:" + correoAnterior);
        String currentPasswordCache = (oldCache != null) ? oldCache.password() : usuarioActualizado.getPassword(); // fallback a BD si no está en Redis

        // 2. Borramos la key anterior
        authRedisTemplate.delete("login:" + correoAnterior);

        // 3. Creamos la nueva entrada con el nuevo correo y la misma contraseña
        AuthLoginRequest updatedCache = new AuthLoginRequest(usuarioActualizado.getCorreo(), encoder.encode(currentPasswordCache));
        authRedisTemplate.opsForValue().set("login:" + usuarioActualizado.getCorreo(), updatedCache);

        return new AuthResponse("Sus datos se han actualizado exitosamente");
    }

    /**
     * Método para actualizar los datos de la empresa
     * @param updateUsuarioRequest parámetro con los datos necesarios para actualizar la empresa
     * @param usuarioActualizado objeto del cual extraemos la empresa al usuario (esto para actualizar sus datos)
     * @return La empresa con sus datos actualizados
     * @nota: Este método se usa en updateUsuario, se opto por crear una clase aparte para no sobrecargar la anterior
     */
    private static Empresa getEmpresa(UpdateUsuarioRequest updateUsuarioRequest, Usuario usuarioActualizado) {
        Empresa empresaActualizada = usuarioActualizado.getEmpresa();

        // Le seteamos los nuevos datos a la empresa actualizada
        empresaActualizada.setNit(updateUsuarioRequest.empresa().nit());
        empresaActualizada.setNombre(updateUsuarioRequest.empresa().nombre());
        empresaActualizada.setPais(updateUsuarioRequest.empresa().pais());
        empresaActualizada.setCiudad(updateUsuarioRequest.empresa().ciudad());
        empresaActualizada.setDireccion(updateUsuarioRequest.empresa().direccion());
        empresaActualizada.setCorreo(updateUsuarioRequest.empresa().correo());
        return empresaActualizada;
    }

    /**
     * Método para eliminar al usuario de la base de datos
     * @param correo parámetro por el cual vamos a buscar al usuario, este campo es único
     * @return un objeto de tipo authResponse que contiene un mensaje de satisfacción
     */
    public AuthResponse deleteUsuario(String correo) {
        // Obtenemos el usuario actual de la sesión
        Usuario usuario = this.getUsuarioByCorreo(correo);

        // Eliminar la key de Redis
        authRedisTemplate.delete("login:" + usuario.getCorreo());

        // Eliminar el usuario de la base de datos
        usuarioRepository.delete(usuario);
        return new AuthResponse("Sus datos han sido eliminados exitosamente");
    }

    /**
     * Método para habilitar o deshabilitar el 2FA
     * @param twoFactorEnabledRequest parámetro con los datos necesarios para habilitar o deshabilitar el 2FA
     * @return un objeto de tipo authResponse que contiene un mensaje de satisfacción
     */
    public AuthResponse autentication2FactorRedis(@Valid TwoFactorEnabledRequest twoFactorEnabledRequest) {
        // Obtenemos el usuario actual de la sesión
        Usuario usuarioActualizado = this.getUsuarioByCorreo(twoFactorEnabledRequest.getCorreo());

        // Guardar los datos de TwoFactor en Redis
        String keyTwoFactor = "Auth2Factor:" + usuarioActualizado.getCorreo();
        twoFactorRedisTemplate.opsForValue().set(keyTwoFactor, twoFactorEnabledRequest);

        // Guardar el estado de TwoFactor en el usuario
        return new AuthResponse("Autenticación de dos pasos activada y almacenada correctamente");
    }

    /**
     * Método para verificar las preguntas de seguridad del 2FA
     * @param correo parámetro por el cual vamos a buscar al usuario, este campo es único
     * @param respuesta1 respuesta a la primera pregunta de seguridad
     * @param respuesta2 respuesta a la segunda pregunta de seguridad
     * @return true si las respuestas son correctas, false en caso contrario
     */
    public boolean verificarPreguntas(String correo, String respuesta1, String respuesta2) {
        String key = "Auth2Factor:" + correo;
        TwoFactorEnabledRequest datos = twoFactorRedisTemplate.opsForValue().get(key);
        if (datos == null) return false;
        return datos.getRespuesta1().equalsIgnoreCase(respuesta1.trim()) &&
                datos.getRespuesta2().equalsIgnoreCase(respuesta2.trim());
    }

}