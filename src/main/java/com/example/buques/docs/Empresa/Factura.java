package com.example.buques.docs.Empresa;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Factura {

    private String id;
    private Integer total;
    @Transient
    private double iva;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;
    private String estado; // este atributo se puede trabajar mejor como una clase enum

    private Set<DetalleFactura> detalles = new HashSet<>();
}
