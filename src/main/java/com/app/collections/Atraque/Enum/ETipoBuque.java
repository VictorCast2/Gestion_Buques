package com.app.collections.Atraque.Enum;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum ETipoBuque {
    PC("Portacontenedores "),
    CG("Carga General"),
    CR("Carga Rodada");

    private final String descripcion;

    public static ETipoBuque obtenerPorDescripcion(String descripcion) {
        for (ETipoBuque tipo : values()) {
            if (tipo.getDescripcion().equalsIgnoreCase(descripcion)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de buque invalido: " + descripcion);
    }

}
