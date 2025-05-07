package com.app.collections.Factura.Enum;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum EOperacion {
    SOLICITUD_CARGA("Solicitud de Carga"),
    SOLICITUD_DESCARGA("Solicitud de Descarga");

    private final String descripcion;

    public static EOperacion obtenerPorDescripcion(String descripcion) {
        for (EOperacion tipo: values()) {
            if (tipo.getDescripcion().equalsIgnoreCase(descripcion)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de proceso invalido: " + descripcion);
    }
}