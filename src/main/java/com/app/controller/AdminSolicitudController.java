package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.service.UserDetailServiceImpl;


@Controller
@RequestMapping("/buques/gestion-solicitud")
public class AdminSolicitudController {

        @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping("/")
    public String adminGestionSolicitud() {
        return "GestionSolicitudes";
    }

}
