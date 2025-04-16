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

    @Field("fecha_llegada")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaLlegada;

    @Field("fecha_salida")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaSalida;

    @Field("estado_solicitud")
    private EResultado estadoSolicitud;

    private Buque buque;

    private Muelle muelle;

    @DBRef
    @Field("admin")
    private List<Usuario> admin = new ArrayList<>();

    @DBRef
    @Field("agente_naviero")
    private List<Usuario> agenteNavieros = new ArrayList<>();
}
