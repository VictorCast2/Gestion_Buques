package com.example.buques.dto.request;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonPropertyOrder({"nit", "nombre", "pais", "ciudad", "direccion", "telefono", "correo", "cantidad_buques"})
public record EmpresaRequest(
        @NotBlank(message = "El NIT no puede estar vacío") String nit,
        @NotBlank(message = "El nombre no puede estar vacío") String nombre,
        @NotBlank(message = "El país no puede estar vacío") String pais,
        @NotBlank(message = "La ciudad no puede estar vacía") String ciudad,
        @NotBlank(message = "La dirección no puede estar vacía") String direccion,
        @Size(min = 9, max = 11) @NotBlank(message = "El teléfono no puede estar vacío") String telefono,
        @Email @NotBlank(message = "El correo no puede estar vacío") String correo,
        @NotBlank(message = "La cantidad de buques no puede estar vacía") String cantidad_buques
        ) {
}