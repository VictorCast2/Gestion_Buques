package com.app.controller;

import com.app.collections.Usuario.Enum.EIdentificacion;
import com.app.collections.Usuario.Usuario;
import com.app.dto.request.EmpresaRequest;
import com.app.dto.request.UpdatePasswordRequest;
import com.app.dto.request.UpdateUsuarioRequest;
import com.app.dto.response.AuthResponse;
import com.app.security.CustomUserDetails;
import com.app.service.EmpresaService;
import com.app.service.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/buques/perfil")
public class PerfilController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private EmpresaService empresaService;

    @RequestMapping("/")
    public String perfil(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "mensaje", required = false) String mensaje, Model model) {
        Usuario usuario = userDetailService.getUsuarioByCorreo(userDetails.getUsername());
        model.addAttribute("usuario", usuario); // datos del usuario
        model.addAttribute("mensaje", mensaje); // mensaje de los distintos formularios, viajan mediate la url
        model.addAttribute("tiposIdentificacion", EIdentificacion.values()); // carga los select con los diferentes tipos de Identificación
        return "Perfil";
    }

    @PostMapping("/actualizar-contraseña")
    @ResponseStatus(value = HttpStatus.OK)
    public String updatePassword(@ModelAttribute @Valid UpdatePasswordRequest updatePasswordRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
        AuthResponse response = userDetailService.updatePassword(updatePasswordRequest, userDetails);
        String mensaje = response.mensaje();
        return "redirect:/buques/perfil/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("/actualizar-datos")
    @ResponseStatus(value = HttpStatus.OK)
    public String updateUsuario(@ModelAttribute @Valid UpdateUsuarioRequest updateUsuarioRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
        AuthResponse response = userDetailService.updateUsuario(updateUsuarioRequest, userDetails);
        String mensaje = response.mensaje();
        return "redirect:/buques/perfil/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("/vincular-empresa")
    @ResponseStatus(value = HttpStatus.OK)
    public String vincularEmpresa(@ModelAttribute @Valid EmpresaRequest empresaRequest, @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        AuthResponse response = empresaService.asignarEmpresa(empresaRequest, userDetails);
        String mensaje = response.mensaje();
        return "redirect:/buques/perfil/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

}
