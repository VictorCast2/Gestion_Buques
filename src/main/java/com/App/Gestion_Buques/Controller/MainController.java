package com.App.Gestion_Buques.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador principal de la aplicación.
 */
@Controller
@RequestMapping("/Api/")
public class MainController {

    /**
     * Redirige a la página de inicio.
     * @return La página de inicio.
     */
    @RequestMapping("/")
    public String Home() {
        return "Index";
    }

}