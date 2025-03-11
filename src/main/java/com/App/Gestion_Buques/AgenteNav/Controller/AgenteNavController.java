package com.App.Gestion_Buques.AgenteNav.Controller;

import lombok.*;
import java.util.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.App.Gestion_Buques.AgenteNav.Entity.AgenteNavEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.App.Gestion_Buques.AgenteNav.Services.UsuarioServices;

@Data
@Controller
@AllArgsConstructor
@RequestMapping("/Api/Usuario")
public class AgenteNavController {

    @Autowired
    private final UsuarioServices usuarioServices;

    @GetMapping("/Home")
    public String Home(Model model) {
        model.addAttribute("usuarios", usuarioServices.encontrarTodosUsuario());
        return "Usuarios";
    }

    @PostMapping("/Add")
    public String registrar(@ModelAttribute AgenteNavEntity usuario) {
        usuarioServices.crearUsuario(usuario);
        return "redirect:/Api/Usuario/Home";
    }

    @PostMapping("/Delete")
    public String eliminar(@RequestParam("id") Long id) {
        usuarioServices.eliminarUsuario(id);
        return "redirect:/Api/Usuario/Home";
    }

    @PostMapping("/Update")
    public String modificar(@ModelAttribute AgenteNavEntity usuario) {
        usuarioServices.modificarUsuario(usuario);
        return "redirect:/Api/Usuario/Home";
    }

    @GetMapping("/Find")
    public String encontrar(@RequestParam("id") Long id, Model model) {
        Optional<AgenteNavEntity> usuario = usuarioServices.encontrarUsuarioPorID(id);
        usuario.ifPresent(u -> model.addAttribute("usuario", u));
        return "UsuarioDetalle";
    }

}