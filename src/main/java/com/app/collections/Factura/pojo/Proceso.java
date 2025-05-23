package com.app.collections.Factura.pojo;

import com.app.collections.Atraque.Atraque;
import com.app.collections.Factura.Enum.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Proceso {

    @Field("tipo_operacion")
    private EOperacion tipoOperacion;
    @Field("tipo_carga")
    private ECarga tipoCarga;
    @Field("tipo_producto")
    private String tipoProducto; // especifica el producto a cargar o descargar
    private int cantidad;
    @Field("precio_unitario")
    private int precioUnitario;
    private int subtotal;
    private String descripcion; // descripción de la solicitud del proceso a realizar
    private EEstadoProceso estadoProceso;

    private Set<Maquina> maquinas = new HashSet<>();
}