package com.app.controller;

import com.app.collections.Muelle.Enum.EEstado;
import com.app.collections.Muelle.Muelle;
import com.app.collections.Usuario.Usuario;
import com.app.dto.request.MuelleRequest;
import com.app.dto.response.AuthResponse;
import com.app.service.MuelleService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.service.UserDetailServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/buques/registro-muelle")
public class RegistroMuelleController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private MuelleService muelleService;

    @GetMapping("/")
    public String adminRegistroMuelle(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "mensaje", required = false) String mensaje, Model model) {

        Usuario usuario = userDetailService.getUsuarioByCorreo(userDetails.getUsername());
        List<Muelle> muelles = muelleService.getMuelles();

        model.addAttribute("usuario", usuario); // datos del usuario
        model.addAttribute("listarMuelles", muelles); // lista de muelles (para mostrar en la tabla)
        model.addAttribute("mensaje", mensaje); // mensaje de los distintos formularios, llegan mediante la url
        model.addAttribute("estadosMuelle", EEstado.values());

        return "RegistroMuelle";
    }

    @PostMapping("/crear-muelle")
    public String crearMuelle(@Valid MuelleRequest muelleRequest) {
        AuthResponse response = muelleService.crearMuelle(muelleRequest);
        String mensaje = response.mensaje();
        return "redirect:/buques/registro-muelle/?mensaje=" + UriUtils.encode(mensaje, StandardCharsets.UTF_8);
    }

}
