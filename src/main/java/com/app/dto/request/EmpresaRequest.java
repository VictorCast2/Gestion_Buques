package com.app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record EmpresaRequest(
        String nit,
        String nombre,
        String pais,
        String ciudad,
        String direccion,
        String telefono,
        String correo) {
}