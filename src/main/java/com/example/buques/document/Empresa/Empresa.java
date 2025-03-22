package com.example.buques.document.Empresa;

import com.example.buques.document.Usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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
@Document(collection = "empresas")
public class Empresa {

    @Id
    private String id;
    private String nit;
    private String nombre;
    private String pais;
    private String ciudad;
    private String direccion;
    private int telefono;
    private String correo;

    @Transient // para que no se guarde en la base de datos
    private int cantidad_buques;

    //considerar si es necesario listar los agentes navieros de una empresa
    @Transient
    @DBRef
    @Field("agentes_navieros")
    private Set<Usuario> agentesNavieros = new HashSet<>();

    private Set<Factura> facturas = new HashSet<>();

}
