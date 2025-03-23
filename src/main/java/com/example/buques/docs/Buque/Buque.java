package com.example.buques.docs.Buque;

import com.example.buques.docs.Buque.pojo.Dimension;
import com.example.buques.docs.Buque.pojo.Inspeccion;
import com.example.buques.docs.Buque.pojo.SolicitudAtraque;
import com.example.buques.docs.Usuario.Usuario;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.HashSet;
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
