package com.app.collections.Usuario.pojo;

import com.app.collections.Usuario.Enum.EEstadoEmpresa;
import lombok.*;
import org.springframework.data.annotation.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Empresa {

    private String nit;
    private String nombre;
    private String pais;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String correo;
    private EEstadoEmpresa estadoEmpresa;

    @Transient // para que no se guarde en la base de datos
    private int cantidad_buques;

}
