package com.app.collections.Usuario.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EPrivacidad {
    PU("Público (todos pueden verlo)"),
    SU("Solo usuarios registrados"),
    SC("Solo mis contactos"),
    SY("Solo yo");

    private final String descripcion;

    public static EPrivacidad obtenerPorDescripcion(String descripcion) {
        for (EPrivacidad tipo : values()) {
            if (tipo.getDescripcion().equalsIgnoreCase(descripcion)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de privacidad invalida: " + descripcion);
    }

}