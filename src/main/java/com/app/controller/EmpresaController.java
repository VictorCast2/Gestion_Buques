package com.app.controller;

import com.app.dto.request.EmpresaRequest;
import com.app.dto.response.EmpresaResponse;
import com.app.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/registro")
    public String registro(Model model) {
        return "Empresa";
    }

    @PostMapping("/registrar")
    public String registrarEmpresa(@Valid @RequestBody EmpresaRequest request, String correoUsuario) {
        EmpresaResponse response = empresaService.asignarEmpresa(request, correoUsuario);
        return "Empresa";
    }

}