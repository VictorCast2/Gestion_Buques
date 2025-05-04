package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/buques/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Área de administración";
    }

}