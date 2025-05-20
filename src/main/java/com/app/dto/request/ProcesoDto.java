package com.app.dto.request;

public record ProcesoDto(String facturaId,
                         String agenteNaviero,
                         String matricula,
                         String tipoOperacion,
                         String tipoCarga,
                         String tipoProducto,
                         int cantidadProducto,
                         String descripcion,
                         String nombreBuque) {
}
