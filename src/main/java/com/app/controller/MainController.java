package com.app.controller;

import com.app.collections.Usuario.Enum.EIdentificacion;
import com.app.collections.Usuario.Usuario;
import com.app.dto.request.UpdatePasswordRequest;
import com.app.dto.request.UpdateUsuarioRequest;
import com.app.dto.response.AuthResponse;
import com.app.security.CustomUserDetails;
import com.app.service.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/buques")
public class MainController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @RequestMapping("/")
    public String index() {
        return "Index";
    }

    @RequestMapping("/quienes-somos")
    public String quienesSomos() {
        return "QuienesSomos";
    }

    @RequestMapping("/servicios")
    public String servicios() {
        return "Servicios";
    }

    @RequestMapping("/noticias")
    public String noticias() {
        return "Noticias";
    }

    @RequestMapping("/testimonios")
    public String testimonios() {
        return "Testimonio";
    }

    @RequestMapping("/contactos")
    public String contatos() {
        return "Contactos";
    }

    @RequestMapping("/perfil")
    public String perfil(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "mensaje", required = false) String mensaje, Model model) {
        Usuario usuario = userDetailService.getUsuarioByCorreo(userDetails.getUsername());
        model.addAttribute("usuario", usuario); // datos del usuario
        model.addAttribute("mensaje", mensaje); // mensaje de los distintos formularios, viajan mediate la url
        model.addAttribute("tiposIdentificacion", EIdentificacion.values()); // carga los select con los diferentes tipos de Identificación
        return "Perfil";
    }

    @PostMapping("/actualizar-contraseña")
    public String updatePassword(@ModelAttribute @Valid UpdatePasswordRequest updatePasswordRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
        AuthResponse response = userDetailService.updatePassword(updatePasswordRequest, userDetails);
        String mensaje = response.mensaje();
        return "redirect:/buques/perfil?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("/actualizar-datos")
    public String updateUsuario(@ModelAttribute @Valid UpdateUsuarioRequest updateUsuarioRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
        AuthResponse response = userDetailService.updateUsuario(updateUsuarioRequest, userDetails);
        String mensaje = response.mensaje();
        return "redirect:/buques/perfil?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @RequestMapping("/notificaciones")
    public String Notificaciones() {
        return "Notificaciones";
    }

}