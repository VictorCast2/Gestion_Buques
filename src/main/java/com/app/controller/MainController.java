package com.app.controller;

import com.app.collections.Usuario.Usuario;
import com.app.dto.request.UpdatePasswordRequest;
import com.app.dto.response.AuthResponse;
import com.app.security.CustomUserDetails;
import com.app.service.UserDetailServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        model.addAttribute("usuario", usuario);
        model.addAttribute("mensaje", mensaje);

        return "Perfil";
    }

    @PostMapping("/actualizar-contraseña")
    public String updatePassword(@ModelAttribute @Valid UpdatePasswordRequest updatePasswordRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
        AuthResponse response = userDetailService.updatePassword(updatePasswordRequest, userDetails);
        String mensaje = response.mensaje();
        return "redirect:/buques/perfil?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @RequestMapping("/notificaciones")
    public String Notificaciones() {
        return "Notificaciones";
    }

}