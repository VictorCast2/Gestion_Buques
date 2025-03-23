package com.example.buques.AgenteNaviero.controller;

import com.example.buques.AgenteNaviero.entity.AgenteNaviero;
import com.example.buques.AgenteNaviero.service.AgenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/agente")
public class AgenteController {

    @Autowired
    private AgenteService agenteService;

    // Metodo para verificar si el agente está logueado
    public boolean agenteLogueado(HttpSession session) {
        return session.getAttribute("agenteLogueado") != null;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "Login";
    }

    // Validacion de credenciales del Agente Naviero
    @PostMapping("/login")
    public String postLogin(@RequestParam String correo, @RequestParam String password, Model model, HttpSession session) {
        try {
            AgenteNaviero agenteNaviero = agenteService.validarCredencialesAgenteNaviero(correo, password);

            if (agenteNaviero != null) {
                session.setAttribute("agenteLogueado", agenteNaviero);
                return "redirect:/api/agente/home";
            } else {
                model.addAttribute("mensajeError", "Email o contraseña incorrectos");
                return "login";
            }
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    // Registro de Agente Naviero
    @PostMapping("/register")
    public String addAgenteNaviero(@ModelAttribute AgenteNaviero agenteNaviero, Model model){
        AgenteNaviero nuevoAgenteNaviero = agenteService.addAgenteNaviero(agenteNaviero);
        model.addAttribute("mensajeExitoso", "Se ha registrado exitosamente");
        return "Login";
    }

    @GetMapping("/cerrar-session")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/api/agente/login";
    }

    @GetMapping("/home")
    public String Home(HttpSession session, Model model) {
        if (!agenteLogueado(session)) {
            return "redirect:/api/agente/login";
        }
        AgenteNaviero agenteNaviero = (AgenteNaviero) session.getAttribute("agenteLogueado");
        if (agenteNaviero != null) {
            model.addAttribute("usuario", agenteNaviero.getNombre());
        }
        return "Index";
    }
}
