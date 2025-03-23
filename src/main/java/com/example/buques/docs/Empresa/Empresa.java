package com.example.buques.docs.Empresa;

import com.example.buques.docs.Empresa.pojo.Factura;
import com.example.buques.docs.Usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;
import java.util.*;

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
    @Email(message = "El correo debe tener un formato válido") // Email verifica que el correo tenga un formato válido
    @NotBlank(message = "El correo no puede estar vacío") // NotBlank solo verifica que no sea null o vacío
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
