package com.app.collections.Factura.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "detalle_procesos")
public class DetalleProcesos {

    @Id
    private String id;
    @Field("descripcion_servicio")
    private String descripcionServicio; // esto lo especifica el admin, donde detalla que se le esta facturando al usuario
    private int cantidad;
    @Field("precio_unitario")
    private int precioUnitario;
    private int subtotal;

    private Proceso proceso;
}
