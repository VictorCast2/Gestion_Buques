package com.app.controller;

import com.app.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/buques")
public class MainController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @RequestMapping("/")
    public String index() {
        return "Index";
    }

    @RequestMapping("/quienes-somos")
    public String quienesSomos() {
        return "QuienesSomos";
    }

    @RequestMapping("/servicios")
    public String servicios() {
        return "Servicios";
    }

    @RequestMapping("/noticias")
    public String noticias() {
        return "Noticias";
    }

    @RequestMapping("/testimonios")
    public String testimonios() {
        return "Testimonio";
    }

    @RequestMapping("/contactos")
    public String contatos() {
        return "Contactos";
    }

    @RequestMapping("/notificaciones")
    public String Notificaciones() {
        return "Notificaciones";
    }

    @RequestMapping("/AgenteNaviero")
    public String AgenteNaviero() {
        return "AgenteNaviero";
    }

    @RequestMapping("/Configuraciones")
    public String Configuraciones() {
        return "Configuraciones";
    }

    @RequestMapping("/Procesos")
    public String Procesos() {
        return "Procesos";
    }

    @RequestMapping("/SolicitudAtraque")
    public String SolicitudAtraque() {
        return "SolicitudAtraque";
    }

}