package com.App.Gestion_Buques.Empresa.Controller;

import lombok.*;
import java.util.Optional;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.App.Gestion_Buques.Empresa.Entity.EmpresaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.App.Gestion_Buques.Empresa.Services.EmpresaServices;

@Data
@Controller
@AllArgsConstructor
@RequestMapping("/Api/Empresa")
public class EmpresaController {

    @Autowired
    private final EmpresaServices empresaServices;

    @GetMapping("/Home")
    public String Home(Model model) {
        model.addAttribute("empresa", empresaServices.encontrarTodasEmpresa());
        return "Empresa";
    }

    @PostMapping("/Add")
    public String registrar(@ModelAttribute EmpresaEntity empresa) {
        empresaServices.crearEmpresa(empresa);
        return "redirect:/Api/Empresa/Home";
    }

    @PostMapping("/Delete")
    public String eliminar(@RequestParam("id") Long id) {
        empresaServices.eliminarEmpresa(id);
        return "redirect:/Api/Empresa/Home";
    }

    @PostMapping("/Update")
    public String modificar(@ModelAttribute EmpresaEntity empresa) {
        empresaServices.modificarEmpresa(empresa);
        return "redirect:/Api/Empresa/Home";
    }

    @GetMapping("/Find")
    public String encontrar(@RequestParam("id") Long id, Model model) {
        Optional<EmpresaEntity> usuario = empresaServices.encontrarEmpresaPorID(id);
        usuario.ifPresent(u -> model.addAttribute("empresa", u));
        return "EmpresaDetalle";
    }

}