package com.app.collections.Atraque;

import com.app.collections.Atraque.pojo.Buque;
import com.app.collections.Atraque.Enum.EResultado;
import com.app.collections.Atraque.pojo.Muelle;
import com.app.collections.Usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private Buque buque;

    private Muelle muelle;

    @DBRef
    @Field("admin")
    private Usuario admin;

    @DBRef
    @Field("agente_naviero")
    private Usuario agenteNaviero;
}
