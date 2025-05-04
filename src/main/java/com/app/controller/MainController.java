package com.app.controller;

import com.app.collections.Usuario.Usuario;
import com.app.dto.request.AtraqueRequest;
import com.app.service.AtraqueService;
import com.app.service.UserDetailServiceImpl;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/buques")
public class MainController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private AtraqueService atraqueService;

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

    @RequestMapping("/AgenteNaviero")
    public String AgenteNaviero() {
        return "AgenteNaviero";
    }

    @RequestMapping("/Configuraciones")
    public String Configuraciones() {
        return "Configuraciones";
    }

    @RequestMapping("/Procesos")
    public String Procesos() {
        return "Procesos";
    }

    @RequestMapping("/SolicitudAtraque")
    public String SolicitudAtraque() {
        return "SolicitudAtraque";
    }

    @PostMapping("/atraques/actualizar")
    public String actualizarSolicitud(
            @RequestParam String id, 
            @ModelAttribute @Valid AtraqueRequest request,
            RedirectAttributes redirectAttributes) {

        atraqueService.actualizarSolicitudAtraque(id, request);

        redirectAttributes.addFlashAttribute("mensaje", "Solicitud de atraque actualizada exitosamente");

        return "redirect:/atraques/lista";
    }

}