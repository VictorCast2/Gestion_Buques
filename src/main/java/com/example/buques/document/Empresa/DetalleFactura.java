package com.example.buques.document.Empresa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
