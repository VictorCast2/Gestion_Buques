package com.App.GestionBuques.Configuration.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador principal de la aplicación.
 */
@Controller
@RequestMapping("/Api")
public class MainController {

    /**
     * Redirige a la página de inicio.
     * @return La página de inicio.
     */
    @RequestMapping("/Home")
    public String Home() {
        return "Index";
    }

}