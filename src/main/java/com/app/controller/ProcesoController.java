package com.app.controller;

import com.app.collections.Factura.Enum.*;
import com.app.collections.Factura.pojo.Proceso;
import com.app.collections.Usuario.Usuario;
import com.app.dto.request.FacturaProcesoRequest;
import com.app.dto.response.AuthResponse;
import com.app.security.CustomUserDetails;
import com.app.service.*;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Data
@Controller
@RequestMapping("/buques/procesos")
public class ProcesoController {

    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    FacturaService facturaService;

    @RequestMapping("/")
    public String Procesos(@AuthenticationPrincipal UserDetails  userDetails, @RequestParam(value = "mensaje", required = false) String mensaje, Model model) {
        Usuario usuario = userDetailService.getUsuarioByCorreo(userDetails.getUsername());
        List<Proceso> procesos = facturaService.getProcesoByUsuario(usuario);
        boolean estadoAprobado = facturaService.validarSolicitudAtraque(usuario);

        model.addAttribute("usuario", usuario); // datos del usuario
        model.addAttribute("procesos", procesos); // lista de procesos (esto es para la tabla)
        model.addAttribute("estadoAprobado", estadoAprobado); // estado del atraque (esto es para el botón de 'Crear Nuevo Registro')
        model.addAttribute("mensaje", mensaje); // mensaje de los distintos formularios, viajan mediate la url
        model.addAttribute("tipoOperacion", EOperacion.values());
        model.addAttribute("tipoCarga", ECarga.values());
        return "Procesos";
    }

    @PostMapping("/registrar-procesos")
    public String registrarProcesos(@ModelAttribute @Valid FacturaProcesoRequest facturaProcesoRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
        AuthResponse response = facturaService.registrarProceso(facturaProcesoRequest, userDetails);
        String mensaje = response.mensaje();
        return "redirect:/buques/procesos/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }
}
