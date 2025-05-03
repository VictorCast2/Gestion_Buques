package com.app.collections.Factura;

import com.app.collections.Factura.Enum.EEstadoFactura;
import com.app.collections.Factura.pojo.Proceso;
import com.app.collections.Usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "facturas")
public class Factura {

    @Id
    private String id;
    @Field("descripcion_servicio")
    private String descripcionServicio; // esto lo especifica el admin, donde detalla que se le esta facturando al usuario

    private Integer total;

    @Transient
    private double iva;

    @Field("fecha_emision")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaEmision;

    @Field("fecha_vencimiento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaVencimiento;

    private EEstadoFactura estado;

    private Set<Proceso> procesos = new HashSet<>();

    @DBRef
    private Set<Usuario> usuarios = new HashSet<>();
}
