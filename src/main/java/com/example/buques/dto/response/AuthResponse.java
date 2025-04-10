package com.example.buques.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"correo", "mensaje", "jwt", "estado"})
public record AuthResponse(
        @JsonProperty("correo") String correo,
        @JsonProperty("mensaje") String mensaje,
        @JsonProperty("jwt") String jwt,
        @JsonProperty("estado") boolean estado
        ) {
}