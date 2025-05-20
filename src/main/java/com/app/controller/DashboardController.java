package com.app.controller;

import com.app.collections.Usuario.Usuario;
import com.app.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/buques/dashboard-admin")
public class DashboardController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping("/")
    public String adminDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Usuario usuario = userDetailService.getUsuarioByCorreo(userDetails.getUsername());
        model.addAttribute("usuario", usuario);
        return "Dashboard";
    }
}
