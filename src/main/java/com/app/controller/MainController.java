package com.app.controller;

import com.app.collections.Usuario.Usuario;
import com.app.collections.Usuario.pojo.Redis.TwoFactorEnabledRequest;
import com.app.dto.response.AuthResponse;
import com.app.service.UserDetailServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@AllArgsConstructor
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

    @RequestMapping("/notificaciones")
    public String Notificaciones(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Usuario usuario = userDetailService.getUsuarioByCorreo(userDetails.getUsername());
        model.addAttribute("usuario", usuario);
        return "Notificaciones";
    }

    @RequestMapping("/panel-control")
    public String AgenteNaviero(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Usuario usuario = userDetailService.getUsuarioByCorreo(userDetails.getUsername());
        model.addAttribute("usuario", usuario);
        return "AgenteNaviero";
    }

    @RequestMapping("/configuraciones")
    public String Configuraciones(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Usuario usuario = userDetailService.getUsuarioByCorreo(userDetails.getUsername());
        model.addAttribute("usuario", usuario);
        return "Configuraciones";
    }

    @PostMapping("/configuraciones/eliminar-usuario")
    public String EliminarUsuario(@Valid @RequestParam("correo") String Correo, RedirectAttributes redirectAttributes) {
        AuthResponse response = userDetailService.deleteUsuario(Correo);
        redirectAttributes.addFlashAttribute("delete", response);
        return "redirect:/auth/logout";
    }

    @PostMapping("/configuraciones/acticcion-autenticacion-2-pasos")
    public String activarAutenticacion2Pasos(
            @ModelAttribute TwoFactorEnabledRequest request,
            RedirectAttributes redirectAttributes) {
        System.out.println("************************************");
        System.out.println("Activando autenticación de dos pasos");
        System.out.println("************************************");
        AuthResponse response = userDetailService.autentication2FactorRedis(request);
        redirectAttributes.addFlashAttribute("twoFactor", response.mensaje());
        return "redirect:/buques/configuraciones";
    }

    @PostMapping("/verificar-autenticacion-2-pasos")
    public String verificarAutenticacion(
            @RequestParam String correo,
            @RequestParam String respuesta1,
            @RequestParam String respuesta2,
            RedirectAttributes redirectAttributes) {
        boolean validado = userDetailService.verificarPreguntas(correo, respuesta1, respuesta2);

        if (validado) {
            // Mal
            redirectAttributes.addFlashAttribute("twoFactorSuccess", "Autenticación verificada con éxito.");
        } else {
            // Bien
            redirectAttributes.addFlashAttribute("twoFactorError", "Respuestas incorrectas. Intenta de nuevo.");
        }
        return "redirect:/";
    }

}