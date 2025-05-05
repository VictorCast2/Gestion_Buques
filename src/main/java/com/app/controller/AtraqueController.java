package com.app.controller;

import com.app.collections.Atraque.Atraque;
import com.app.collections.Atraque.Enum.ETipoBuque;
import com.app.collections.Usuario.Usuario;
import com.app.dto.request.AtraqueRequest;
import com.app.dto.response.AuthResponse;
import com.app.security.CustomUserDetails;
import com.app.service.AtraqueService;
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
import java.util.List;

@Controller
@RequestMapping("/buques/solicitud-atraque")
public class AtraqueController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private AtraqueService atraqueService;

    @RequestMapping("/")
    public String SolicitudAtraque(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "mensaje", required = false) String mensaje, Model model) {

        Usuario usuario = userDetailService.getUsuarioByCorreo(userDetails.getUsername());
        List<Atraque> atraques = atraqueService.getAtraquesByUsuario(usuario);

        model.addAttribute("usuario", usuario); // datos del usuario
        model.addAttribute("listarAtraques", atraques); // lista de atraques (esto es para la tabla)
        model.addAttribute("mensaje", mensaje); // mensaje de los distintos formularios, llegan mediante la url
        model.addAttribute("tiposBuque", ETipoBuque.values());

        return "SolicitudAtraque";
    }

    @PostMapping("/crear-solicitud")
    public String crearSolicitudAtraque(@ModelAttribute @Valid AtraqueRequest atraqueRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
        AuthResponse response = atraqueService.crearSolicitudAtraque(atraqueRequest, userDetails);
        String mensaje = response.mensaje();
        return "redirect:/buques/solicitud-atraque/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("/actualizar-solicitud/{id}")
    public String actualizarSolicitud(@ModelAttribute @Valid AtraqueRequest atraqueRequest, @PathVariable String id) {
        AuthResponse response = atraqueService.updateSolicitudAtraque(atraqueRequest, id);
        String mensaje = response.mensaje();
        return "redirect:/buques/solicitud-atraque/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

    @PostMapping("eliminar-solicitud/{id}")
    public String eliminarSolicitudAtraque(@PathVariable String id) {
        AuthResponse response = atraqueService.deleteSolicitudAtraque(id);
        String mensaje = response.mensaje();
        return "redirect:/buques/solicitud-atraque/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }
}
