package com.example.buques.docs.Empresa;

import com.example.buques.docs.Empresa.pojo.Factura;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "empresas")
public class Empresa {

    @Id
    @BsonProperty("_id")
    private String id;

    @BsonProperty("nit")
    private String nit;
    @BsonProperty("nombre")
    private String nombre;
    @BsonProperty("pais")
    private String pais;
    @BsonProperty("ciudad")
    private String ciudad;
    @BsonProperty("direccion")
    private String direccion;
    @BsonProperty("telefono")
    private String telefono;
    @BsonProperty("correo")
    private String correo;

    @Transient // para que no se guarde en la base de datos
    private int cantidad_buques;

    @BsonProperty("facturas")
    private Set<Factura> facturas = new HashSet<>();

}