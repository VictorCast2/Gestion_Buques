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
@RequestMapping("/api/usuario")
public class AgenteNavController {

    @Autowired
    private final UsuarioServices usuarioServices;

    @GetMapping("/home")
    public String Home(Model model) {
        model.addAttribute("usuarios", usuarioServices.encontrarTodosUsuario());
        return "Usuarios";
    }

    @PostMapping("/add")
    public String registrar(@ModelAttribute AgenteNavEntity usuario) {
        usuarioServices.crearUsuario(usuario);
        return "redirect:/Api/Usuario/Home";
    }

    @PostMapping("/delete")
    public String eliminar(@RequestParam("id") Long id) {
        usuarioServices.eliminarUsuario(id);
        return "redirect:/Api/Usuario/Home";
    }

    @PostMapping("/update")
    public String modificar(@ModelAttribute AgenteNavEntity usuario) {
        usuarioServices.modificarUsuario(usuario);
        return "redirect:/Api/Usuario/Home";
    }

    @GetMapping("/find")
    public String encontrar(@RequestParam("id") Long id, Model model) {
        Optional<AgenteNavEntity> usuario = usuarioServices.encontrarUsuarioPorID(id);
        usuario.ifPresent(u -> model.addAttribute("usuario", u));
        return "UsuarioDetalle";
    }

}