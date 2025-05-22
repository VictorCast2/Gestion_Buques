package com.app.controller.AdminController;

import com.app.collections.Atraque.Atraque;
import com.app.collections.Usuario.Usuario;
import com.app.service.AtraqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.service.UserDetailServiceImpl;


import java.util.List;

@Controller
@RequestMapping("/buques/horarios")
public class HorarioController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private AtraqueService atraqueService;

    @GetMapping("/")
    public String adminHorarios(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Usuario usuario = userDetailService.getUsuarioByCorreo(userDetails.getUsername());
        List<Atraque> atraques = atraqueService.getAtraquesByEstadoAprobado();

        model.addAttribute("usuario", usuario); // datos del usuario en sesión
        model.addAttribute("listarAtraques", atraques); // lista de atraques para la tabla
        return "Horarios";
    }

}
