package com.app.service;

import com.app.collections.Usuario.Enum.ERol;
import com.app.collections.Usuario.Usuario;
import com.app.collections.Usuario.pojo.Empresa;
import com.app.dto.request.EmpresaRequest;
import com.app.dto.response.EmpresaResponse;
import com.app.repository.UsuarioRepository;
import com.app.utils.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public EmpresaResponse asociarEmpresaAUsuario(@Valid EmpresaRequest request, String correoUsuario) {

        Usuario usuario = usuarioRepository.findByCorreo(correoUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + correoUsuario));

        if (usuario.getEmpresa() != null) {
            return new EmpresaResponse("El usuario ya tiene una empresa asociada.");
        }

        Empresa empresa = Empresa.builder()
                .nit(request.nit())
                .nombre(request.nombre())
                .pais(request.pais())
                .ciudad(request.ciudad())
                .direccion(request.direccion())
                .telefono(request.telefono())
                .correo(request.correo())
                .build();

        usuario.setEmpresa(empresa);
        usuario.setRol(ERol.AGENTE_NAVIERO);
        usuarioRepository.save(usuario);
        return new EmpresaResponse("Empresa registrada y asociada exitosamente al usuario.");
    }

}