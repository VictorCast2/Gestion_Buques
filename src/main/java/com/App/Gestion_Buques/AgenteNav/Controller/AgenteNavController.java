package com.App.Gestion_Buques.AgenteNav.Controller;

import lombok.*;
import java.util.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.App.Gestion_Buques.AgenteNav.Entity.AgenteNavEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.App.Gestion_Buques.AgenteNav.Services.AgenteServices;

@Data
@Controller
@AllArgsConstructor
@RequestMapping("/api/usuario")
public class AgenteNavController {

    @Autowired
    private final AgenteServices agenteServices;

    @GetMapping("/home")
    public String Home(Model model) {
        model.addAttribute("usuarios", agenteServices.getAgentes());
        return "usuarios";
    }

    @PostMapping("/add")
    public String registrar(@ModelAttribute AgenteNavEntity usuario) {
        agenteServices.addUsuario(usuario);
        return "redirect:/api/usuario/home";
    }

    @PostMapping("/delete")
    public String eliminar(@RequestParam("id") Long id) {
        agenteServices.deleteAgente(id);
        return "redirect:/api/usuario/home";
    }

    @PostMapping("/update")
    public String modificar(@ModelAttribute AgenteNavEntity usuario) {
        agenteServices.updateUsuario(usuario);
        return "redirect:/api/usuario/home";
    }

    @GetMapping("/find")
    public String encontrar(@RequestParam("id") Long id, Model model) {
        Optional<AgenteNavEntity> usuario = agenteServices.getAgenteById(id);
        usuario.ifPresent(u -> model.addAttribute("usuario", u));
        return "UsuarioDetalle";
    }

}