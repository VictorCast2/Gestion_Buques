package com.app.controller;

import com.app.collections.Atraque.Atraque;
import com.app.collections.Usuario.Usuario;
import com.app.dto.response.AuthResponse;
import com.app.service.AtraqueService;
import com.app.service.EmpresaService;
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
public class AdminSolicitudController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/")
    public String adminGestionSolicitud(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "mensaje", required = false) String mensaje, Model model) {

        Usuario usuario = userDetailService.getUsuarioByCorreo(userDetails.getUsername());
        List<Usuario> agenteNaviero = userDetailService.getAgentesNavieros();

        model.addAttribute("usuario", usuario); // datos del usuario en sesión
        model.addAttribute("listarEmpresas", agenteNaviero); // Lista de solicitudes a una empresa
        model.addAttribute("mensaje", mensaje); // mensaje de los distintos formularios, llegan mediante la url

        return "GestionSolicitudes";
    }

    @PostMapping("/validar-empresa/{id}")
    public String validarEmpresa(@PathVariable String id, @RequestParam boolean aprovada) {
        AuthResponse response = empresaService.validarEmpresa(id, aprovada);
        String mensaje = response.mensaje();
        return "redirect:/buques/gestion-solicitud/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

}
