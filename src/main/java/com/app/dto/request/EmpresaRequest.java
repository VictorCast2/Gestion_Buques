package com.app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmpresaRequest(
        @NotBlank String nit,
        @NotBlank String nombre,
        @NotBlank String pais,
        @NotBlank String ciudad,
        @NotBlank String direccion,
        @NotBlank String telefono,
        @Email String correo) {
}