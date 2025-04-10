package com.example.buques.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"nit", "nombre", "pais", "ciudad", "direccion", "telefono", "correo", "jwt", "cantidad_buques"})
public record EmpresaResponse(
        @JsonProperty("nit") String nit,
        @JsonProperty("nombre") String nombre,
        @JsonProperty("pais") String pais,
        @JsonProperty("ciudad") String ciudad,
        @JsonProperty("direccion") String direccion,
        @JsonProperty("telefono") String telefono,
        @JsonProperty("correo") String correo,
        @JsonProperty("jwt") String jwt,
        @JsonProperty("cantidad_buques") int cantidad_buques
        ) {
}