package com.app.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUsuarioRequest(@NotBlank String tipoIdentificacion,
                                   @NotBlank String numeroIdentificacion,
                                   @NotBlank String nombres,
                                   @NotBlank String apellidos,
                                   @NotBlank String telefono,
                                   @Email(message = "correo invalido") String correo,
                                   @Valid EmpresaRequest empresa) {
}
