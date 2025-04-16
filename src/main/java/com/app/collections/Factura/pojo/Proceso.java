package com.app.collections.Factura.pojo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Proceso {

    @Field("tipo_operacion")
    private String tipoOperacion; // considerar trabajar este atributo como un Enum
    @Field("tipo_carga")
    private String tipoCarga; // considerar trabajar este atributo como un Enum
    @Field("tipo_producto")
    private String tipoProducto; // considerar trabajar este atributo como un Enum
    private int cantidad;
    @Field("peso_total")
    private double pesoTotal;
    private String descripcion;

    private Set<Maquina> maquinas = new HashSet<>();
}
