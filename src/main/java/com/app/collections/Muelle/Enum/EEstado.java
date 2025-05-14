package com.app.collections.Muelle.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EEstado {
    DISPONIBLE("DISPONIBLE"),
    EN_USO("EN USO"),
    EN_MANTENIMIENTO("EN MANTENIMIENTO"),
    FUERA_DE_SERVICIO("FUERA DE SERVICIO");

    private final String descripcion;
}
