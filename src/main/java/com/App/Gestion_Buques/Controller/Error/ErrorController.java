package com.App.Gestion_Buques.Controller.Error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/error")
public class ErrorController {

    /**
     * Redirige a la página de error correspondiente.
     * @param Code El código de error.
     * @return La página de error correspondiente.
     */
    @GetMapping("/{Code}")
    public String error(@PathVariable int Code) {
        return switch (Code) {
            case 300 -> "/error/300";
            case 400 -> "/error/400";
            case 403 -> "/error/403";
            case 404 -> "/error/404";
            case 500 -> "/error/500";
            default -> "/error/Error";
        };
    }

}