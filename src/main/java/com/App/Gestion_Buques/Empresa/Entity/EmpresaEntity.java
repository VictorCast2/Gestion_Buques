package com.App.Gestion_Buques.Empresa.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@Table(name = "Usuario")
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "Name")
    private String name;

    @Column(name = "NIT", nullable = false, unique = true)
    private String NIT;

    @Column(name = "Pais")
    private String Pais;

    @Column(name = "Ciudad")
    private String Ciudad;

    @Column(name = "Direccion")
    private String Direccion;

    @Column(name = "Telefono")
    private String Telefono;

    @Column(name = "Correo")
    private String Correo;

    @Column(name = "Cantidad_Buques")
    private int Cantidad_Buques;

}