package com.App.Gestion_Buques.Controller.Error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Error")
public class ErrorController {

    /**
     * Redirige a la página de error correspondiente.
     * @param Code El código de error.
     * @return La página de error correspondiente.
     */
    @GetMapping("/{Code}")
    public String error(@PathVariable int Code) {
        return switch (Code) {
            case 300 -> "/Error/300";
            case 400 -> "/Error/400";
            case 403 -> "/Error/403";
            case 404 -> "/Error/404";
            case 500 -> "/Error/500";
            default -> "/Error/Error";
        };
    }

}