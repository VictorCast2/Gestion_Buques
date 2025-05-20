package com.app.collections.Factura;

import com.app.collections.Atraque.Atraque;
import com.app.collections.Factura.Enum.EEstadoFactura;
import com.app.collections.Factura.pojo.Proceso;
import com.app.collections.Usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;
import java.time.LocalDateTime;
import java.util.*;

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

    private double total;

    @Transient
    private double iva;

    @Field("fecha_emision")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaEmision;

    @Field("fecha_vencimiento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaVencimiento;

    private EEstadoFactura estado;

    private List<Proceso> procesos = new ArrayList<>();

    @DBRef
    private Set<Usuario> agenteNaviero = new HashSet<>();

    @DBRef
    private Usuario admin;

    @DBRef
    private Atraque atraque;
}