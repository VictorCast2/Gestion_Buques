package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/buques/procesos")
public class ProcesoController {

    @RequestMapping("/")
    public String Procesos() {
        return "Procesos";
    }
}
