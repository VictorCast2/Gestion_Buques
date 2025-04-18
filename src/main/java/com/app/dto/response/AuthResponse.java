package com.app.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"correo", "mensaje", "jwt", "estado"})
public record AuthResponse(String correo, String mensaje, String jwt, boolean estado) {
}