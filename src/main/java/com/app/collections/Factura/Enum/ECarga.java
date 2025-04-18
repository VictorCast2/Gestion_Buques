package com.app.collections.Factura.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ECarga {
    RO("Carga RORO"), // TIPO DE CARGA PARA VEHICULOS
    CG("Carga Suelta"),
    CN("Contenedores"),
    GL("Granel Limpio"),
    GS("Granel Sucio"),
    LQ("Liquido");

    private final String descripcion;

    private static ECarga obtenerPorDescripcion(String descripcion) {
        for (ECarga tipo : values()) {
            if (tipo.getDescripcion().equalsIgnoreCase(descripcion)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de carga invalida: " + descripcion);
    }
}
