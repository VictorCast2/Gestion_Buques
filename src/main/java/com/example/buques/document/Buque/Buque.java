package com.example.buques.document.Buque;

import com.example.buques.document.Usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "buques")
public class Buque {

    @Id
    private String id;
    private String imagen;
    private String matricula;
    private String nombre;
    private String tipoBuque;
    private Dimension dimensiones;

    @DBRef
    @Field("agente_naviero")
    private Usuario agenteNaviero;

    private Set<Inspeccion> inspecciones = new HashSet<>();
    @Field("solicitudes_atraque")
    private Set<SolicitudAtraque> solicitudAtraques = new HashSet<>();
}
