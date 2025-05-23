package com.app.collections.Factura.pojo;

import com.app.collections.Muelle.Enum.EEstado;
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