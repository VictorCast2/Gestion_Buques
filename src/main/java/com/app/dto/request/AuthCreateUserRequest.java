package com.app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthCreateUserRequest(@NotBlank String tipoIdentificacion,
                                    @NotBlank String numeroIdentificacion,
                                    @NotBlank String nombres,
                                    @NotBlank String apellidos,
                                    @NotBlank String telefono,
                                    @Email(message = "correo invalido") String correo,
                                    @NotBlank @Size(min = 4) String password) {
}
