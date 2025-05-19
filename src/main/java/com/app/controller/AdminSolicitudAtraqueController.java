package com.app.controller;

import com.app.collections.Atraque.Atraque;
import com.app.collections.Muelle.Muelle;
import com.app.collections.Usuario.Usuario;
import com.app.dto.request.ValidarSolicitudAtraque;
import com.app.dto.response.AuthResponse;
import com.app.service.AtraqueService;
import com.app.service.MuelleService;
import com.app.utils.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.app.service.UserDetailServiceImpl;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;


@Controller
@RequestMapping("/buques/gestion-solicitud")
public class AdminSolicitudAtraqueController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private AtraqueService atraqueService;

    @Autowired
    private MuelleService muelleService;

    @GetMapping("/admin-atraque")
    public String adminGestionSolicitud(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "mensaje", required = false) String mensaje, Model model) {
        Usuario usuario = userDetailService.getUsuarioByCorreo(userDetails.getUsername());
        List<Atraque> atraques = atraqueService.getAtraquesByEstadoPendiente();
        List<Muelle> muellesDisponibles = muelleService.getMuelleByEstadoDisponible();

        model.addAttribute("usuario", usuario); // datos del usuario en sesión
        model.addAttribute("listarAtraques", atraques); // lista de atraques para el formulario
        model.addAttribute("muellesDisponibles", muellesDisponibles); // para el campo <select> del formulario
        model.addAttribute("mensaje", mensaje); // mensaje de los distintos formularios, llegan mediante la url
        return "AdminSolicitudAtraque";
    }

    @PostMapping("/validar-solicitud-atraque/{atraqueId}")
    public String validarSolicitudAtraque(@Valid @ModelAttribute ValidarSolicitudAtraque validarSolicitudAtraque,
                                          @AuthenticationPrincipal CustomUserDetails userDetails,
                                          @PathVariable String atraqueId,
                                          @RequestParam boolean estado) {
        AuthResponse response = atraqueService.validarSolicitudAtraque(validarSolicitudAtraque, userDetails, atraqueId, estado);
        String mensaje = response.mensaje();
        return "redirect:/buques/gestion-solicitud/admin-atraque?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

}
