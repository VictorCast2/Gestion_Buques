package com.example.buques.docs.Empresa.pojo;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleFactura {

    @Field("descripcion_servicio")
    private String descripcionServicio;
    private int cantidad;
    private int precioUnitario;
    private int subtotal;
}
