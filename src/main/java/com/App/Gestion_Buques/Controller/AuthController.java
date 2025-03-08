package com.App.Gestion_Buques.Controller;

import org.springframework.ui.Model;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador de autenticación.
 */
@Controller
@RequestMapping("/Api/Auth/")
public class AuthController {

    @GetMapping("/Login")
    public String Login(@RequestParam(name = "Error", required = false) String Error, Principal principal,
                        @RequestParam(name = "Logout",required = false) String Logout, Model model,
                        RedirectAttributes attributes) {
        if(Error != null){
            model.addAttribute("Error", "El Usuario o La Contraseña es incorrecta");
        }
        if (principal != null){
            attributes.addFlashAttribute("Warning","Ya has iniciado sesión");
            return "redirect:/";
        }
        if(Logout != null){
            model.addAttribute("Success","Has cerrado sesión correctamente... ¡Hasta la próxima!");
        }
        return "Login";
    }

}