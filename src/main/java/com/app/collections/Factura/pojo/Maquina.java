package com.app.collections.Factura.pojo;

import com.app.collections.Atraque.Enum.EEstado;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Maquina {

    private String nombre;
    private EEstado estado;
    private int cantidad;
}
