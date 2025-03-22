package com.example.buques.docs.Empresa;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleFactura {

    private String descripcionServicio;
    private int cantidad;
    private int precioUnitario;
    private int subtotal;
}
