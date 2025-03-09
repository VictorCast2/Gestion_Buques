package com.App.Gestion_Buques.Usuario.Controller;

import lombok.*;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.App.Gestion_Buques.Usuario.Entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.App.Gestion_Buques.Usuario.Services.UsuarioServices;

@Data
@Controller
@AllArgsConstructor
@RequestMapping("/Api/Usuario/")
public class UsuarioController {

    @Autowired
    private final UsuarioServices usuarioServices;

    @GetMapping("/Home")
    public String Home(Model model) {
        model.addAttribute("usuarios", usuarioServices.encontrarTodosUsuario());
        return "Usuarios";
    }

    @PostMapping("/Add")
    public String registrar(@ModelAttribute UsuarioEntity usuario) {
        usuarioServices.crearUsuario(usuario);
        return "redirect:/Api/Usuario/Home";
    }

    @PostMapping("/Delete")
    public String eliminar(@RequestParam("id") Long id) {
        usuarioServices.eliminarUsuario(id);
        return "redirect:/Api/Usuario/Home";
    }

    @PostMapping("/Update")
    public String modificar(@ModelAttribute UsuarioEntity usuario) {
        usuarioServices.modificarUsuario(usuario);
        return "redirect:/Api/Usuario/Home";
    }

    @GetMapping("/Find")
    public String encontrar(@RequestParam("id") Long id, Model model) {
        Optional<UsuarioEntity> usuario = usuarioServices.encontrarUsuarioPorID(id);
        usuario.ifPresent(u -> model.addAttribute("usuario", u));
        return "UsuarioDetalle";
    }

}