package com.app.controller.AdminController;

import com.app.collections.Usuario.Usuario;
import com.app.dto.request.ProcesoDto;
import com.app.dto.response.AuthResponse;
import com.app.service.FacturaService;
import com.app.utils.CustomUserDetails;
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
public class AdminSolicitudProcesos {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private FacturaService facturaService;

    @GetMapping("/admin-procesos")
    public String AdminProcesos(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "mensaje", required = false) String mensaje, Model model) {
        Usuario usuario = userDetailService.getUsuarioByCorreo(userDetails.getUsername());
        List<ProcesoDto> procesosPendientes = facturaService.getProcesosPendientes();

        model.addAttribute("usuario", usuario); // datos del usuario en sesión
        model.addAttribute("procesosPendientes", procesosPendientes); // lista de procesos pendientes para la tabla
        model.addAttribute("mensaje", mensaje); // mensaje de los distintos formularios, llegan mediante la url
        return "AdminProcesos";
    }

    @PostMapping("/validar-procesos/{facturaId}")
    public String validarProcesos(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @PathVariable String facturaId,
                                  @RequestParam(required = false) String descripcionServicio,
                                  @RequestParam boolean estado) {
        AuthResponse response = facturaService.validarProceso(userDetails, facturaId, descripcionServicio, estado);
        String mensaje = response.mensaje();
        return "redirect:/buques/gestion-solicitud/admin-procesos?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

}
