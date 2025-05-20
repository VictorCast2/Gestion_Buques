package com.app.collections.Atraque;

import com.app.collections.Atraque.Enum.EResultado;
import com.app.collections.Atraque.pojo.*;
import com.app.collections.Muelle.Muelle;
import com.app.collections.Usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "atraques")
public class Atraque {

    @Id
    private String id;

    @Field("pais_procedencia")
    private String paisProcedencia;
    @Field("ciudad_procedencia")
    private String ciudadProcedencia;
    @Field("puerto_procedencia")
    private String puertoProcedencia;

    @Field("pais_destino")
    private String paisDestino;
    @Field("ciudad_destino")
    private String ciudadDestino;
    @Field("puerto_destino")
    private String puertoDestino;

    @Field("fecha_llegada")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaLlegada;

    @Field("fecha_salida")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaSalida;

    @Field("estado_solicitud")
    private EResultado estadoSolicitud;

    @Field("descripcion_solicitud")
    private String descripcionSolicitud; // descripción dada por el admin cuando acepta la solicitud

    @Field("fecha_aprobacion")
    private LocalDateTime fechaAprobacion;

    private Buque buque;

    @DBRef
    private Muelle muelle;

    @DBRef
    @Field("admin")
    private Usuario admin;

    @DBRef
    @Field("agente_naviero")
    private Usuario agenteNaviero;
}
